package com.playtech.ptargame3.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtech.ptargame3.server.database.DatabaseAccess;
import com.playtech.ptargame3.server.database.model.User;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * Simple http server from core java.
 *
 * @author Janno
 */
public final class WebListener {
    private static final Logger logger = Logger.getLogger(WebListener.class.getName());
    private static final String CTX_LEADERBOARD = "/leaderboard";
    private static final String CTX_USER = "/user";
    private static final String CTX_SERVER = "/server";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String ENCODING = "UTF-8";

    private final int port;
    private final HttpServer s;
    private final DatabaseAccess databaseAccess;

    public WebListener(int port, DatabaseAccess databaseAccess) throws IOException {
        this.port = port;
        this.databaseAccess = databaseAccess;
        s = HttpServer.create(new InetSocketAddress(getPort()), 0);
    }

    private int getPort() {
        return this.port;
    }

    public void start() {
        HttpContext ctxServer = s.createContext(CTX_SERVER);
        ctxServer.setHandler(this::handleExchange);

        HttpContext ctxLeaderboard = s.createContext(CTX_LEADERBOARD);
        ctxLeaderboard.setHandler(this::handleExchange);

        HttpContext ctxCompetitor = s.createContext(CTX_USER);
        ctxCompetitor.setHandler(this::handleExchange);

        if (s.getExecutor()!=null)
            throw new IllegalStateException();

        s.setExecutor(createExecutor());
        s.start();

        logger.info("Started HttpUtilityServer on port " + s.getAddress().getPort());
    }

    private void handleExchange(HttpExchange httpExchange) throws IOException {
        try {
            URI uri = relative(httpExchange);
            String path = uri.getPath();

            if (logger.isLoggable(Level.INFO)) {
                logger.info(String.format("HttpUtilityServer Serving uri: %s, from %s", httpExchange.getRequestURI(), httpExchange.getRemoteAddress()));
            }

            processMapping(httpExchange, path);
        }catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Unable to handle request: "+httpExchange, e);

            byte[] b = e.getMessage().getBytes();
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, b.length);
            try(OutputStream out= httpExchange.getResponseBody()){
                out.write(b);
            }
        } finally {
            httpExchange.close();
        }
    }

    private void processMapping(HttpExchange httpExchange, String path) throws IOException {
        switch(path){
            case "status":
                checkContext(httpExchange, CTX_SERVER);
                checkRequestMethod(httpExchange, METHOD_GET);
                getStatusRequest(httpExchange);
                break;
            case "create":
                checkContext(httpExchange, CTX_USER);
                checkRequestMethod(httpExchange, METHOD_POST);
                createUserRequest(httpExchange);
                break;
            case "list":
                checkContext(httpExchange, CTX_USER);
                checkRequestMethod(httpExchange, METHOD_GET);
                listUsersRequest(httpExchange);
                break;
            default:
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format("HttpUtilityServer Cannot serve uri: %s, invalid path %s", httpExchange.getRequestURI(), path));
                }
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0L);
                break;
        }
    }

    private void checkContext(HttpExchange httpExchange, String expectedPath) {
        if ( !expectedPath.equals( httpExchange.getHttpContext().getPath() ) ) {
            throw new RuntimeException( "Invalid path to context. Expected: " + expectedPath + ", actual: " + httpExchange.getHttpContext().getPath() );
        }
    }

    private void checkRequestMethod(HttpExchange httpExchange, String expectedMethod) {
        if ( !expectedMethod.equals( httpExchange.getRequestMethod() ) ) {
            throw new RuntimeException( "Invalid request method. Expected: " + expectedMethod + ", actual: " + httpExchange.getRequestMethod() );
        }
    }

    private void getStatusRequest( HttpExchange httpExchange ) {
        try {
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, "OK");
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void createUserRequest( HttpExchange httpExchange ) {
        try {
            Map<String, String> params = parsePostParameters(httpExchange);
            String name = params.get("name");
            String email = params.get("email");
            databaseAccess.getUserDatabase().addUser(name, email);
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, "OK");
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void listUsersRequest( HttpExchange httpExchange ) {
        try {
            Collection<User> users = databaseAccess.getUserDatabase().getUsers();
            ObjectMapper objectMapper = new ObjectMapper();
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, objectMapper.writeValueAsString(users));
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void writeResponse( HttpExchange httpExchange, int errorCode, String response ) {
        try {
            byte[] b = response.getBytes(ENCODING);
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders( errorCode, b.length);
            httpExchange.getResponseBody().write( b);
        } catch (Exception e) {
            logger.log(Level.INFO, "Unable to send response", e);
        }
    }

    private void writeResponse( HttpExchange httpExchange, int errorCode ) {
        try {
            byte[] b = "Bad request".getBytes(ENCODING);
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders( errorCode, b.length);
            httpExchange.getResponseBody().write( b);
        } catch (Exception e) {
            logger.log(Level.INFO, "Unable to send response", e);
        }
    }

    public void stop() {
        if (s.getExecutor()==null)//stop before start
            return;

        Executor ex = s.getExecutor();
        if (ex instanceof ExecutorService){
            ((ExecutorService) ex).shutdown();
        }
        s.stop(0);
        if (logger.isLoggable(Level.INFO)) {
            logger.info("Stopped HttpUtilityServer");
        }
    }

    private static Executor createExecutor() {
        ThreadPoolExecutor e = new ThreadPoolExecutor(1, 2, 6, TimeUnit.MINUTES, new LinkedBlockingQueue<>(1024));
        ThreadGroup g = Thread.currentThread().getThreadGroup();
        e.setThreadFactory(new ThreadFactory() {
            AtomicInteger count = new AtomicInteger();
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(g, r, String.format("http-%d", count.incrementAndGet()));
            }
        });
        return e;
    }

    private static URI relative(HttpExchange httpExchange) throws IOException{
        try{
            return new URI(httpExchange.getHttpContext().getPath()).relativize(httpExchange.getRequestURI());
        } catch(URISyntaxException _x){
            throw new IOException(_x);
        }
    }

//	private static Map<String, String> parseQueryParameters(HttpExchange httpExchange) {
//		return parseJsonParameters(httpExchange.getRequestURI().getQuery());
//	}

    private static Map<String, String> parsePostParameters(HttpExchange httpExchange) throws IOException {
        // lets get post parameters
        ByteArrayOutputStream messageBody = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        InputStream in = httpExchange.getRequestBody();
        int pos;
        while ( (pos = in.read(buffer)) != -1 ) {
            messageBody.write(buffer, 0, pos);
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("HttpUtilityServer post parameters: %s", new String(messageBody.toByteArray(), ENCODING)));
        }

        // lets parse parameters string
        return parseJsonParameters(new String(messageBody.toByteArray(), ENCODING));
    }

    private static Map<String, String> parseJsonParameters(String paramStr) {
        Map<String, String> queryParameters = new LinkedHashMap<>();
        String[] parameters = Optional.of(paramStr).orElse("").split("&"); // key1=value1&key2=value2&...x=&y=111&&&
        for (String p: parameters) {
            if (p.length()==0)
                continue;

            final int idx = p.indexOf('=');

            if (idx==0){//skip =value (i.e. no key)
                continue;
            }
            String key = idx<0? p : p.substring(0, idx);
            String value = idx<0? "" : p.substring(idx+1, p.length());

            queryParameters.put(key, value);
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("HttpUtilityServer uri parameters: %s", queryParameters));
        }
        return queryParameters;
    }

}
