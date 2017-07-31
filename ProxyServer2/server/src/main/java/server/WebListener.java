package server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final String CTX_LOCATION = "/location";
    private static final String CTX_COMPETITOR = "/competitor";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String ENCODING = "UTF-8";

    private final int port;
    private final HttpServer s;

    public WebListener(int port) throws IOException {
        this.port = port;
        s = HttpServer.create(new InetSocketAddress(getPort()), 0);
    }

    private int getPort() {
        return this.port;
    }

    public void start() throws IOException{
        HttpContext ctxLocation = s.createContext(CTX_LOCATION);
        ctxLocation.setHandler((xchgLocation)-> handleExchange(xchgLocation));

        HttpContext ctxCompetitor = s.createContext(CTX_COMPETITOR);
        ctxCompetitor.setHandler((xchgCompetitor)-> handleExchange(xchgCompetitor));

        if (s.getExecutor()!=null)
            throw new IllegalStateException();

        s.setExecutor(createExecutor());
        s.start();

        logger.info("Started HttpUtilityServer on port " + s.getAddress().getPort());
    }

    private void handleExchange(HttpExchange xchg) throws IOException {
        try {
            URI uri = relative(xchg);
            String path = uri.getPath();

            if (logger.isLoggable(Level.INFO)) {
                logger.info(String.format("HttpUtilityServer Serving uri: %s, from %s", xchg.getRequestURI(), xchg.getRemoteAddress()));
            }

            processMapping(xchg, path);
        }catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Unable to handle request: "+xchg, e);

            byte[] b = e.getMessage().getBytes();
            xchg.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, b.length);
            try(OutputStream out= xchg.getResponseBody()){
                out.write(b);
            }
        } finally {
            xchg.close();
        }
    }

    private void processMapping(HttpExchange xchg, String path) throws IOException {
        switch(path){
            case "status":
                checkContext(xchg, CTX_COMPETITOR);
                checkRequestMethod(xchg, METHOD_GET);
                getStatusRequest(xchg);
                break;
            default:
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format("HttpUtilityServer Cannot serve uri: %s, invalid path %s", xchg.getRequestURI(), path));
                }
                xchg.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0L);
                break;
        }
    }

    private void checkContext(HttpExchange xchg, String expectedPath) {
        if ( !expectedPath.equals( xchg.getHttpContext().getPath() ) ) {
            throw new RuntimeException( "Invalid path to context. Expected: " + expectedPath + ", actual: " + xchg.getHttpContext().getPath() );
        }
    }

    private void checkRequestMethod(HttpExchange xchg, String expectedMethod) {
        if ( !expectedMethod.equals( xchg.getRequestMethod() ) ) {
            throw new RuntimeException( "Invalid request method. Expected: " + expectedMethod + ", actual: " + xchg.getRequestMethod() );
        }
    }

    private void getStatusRequest( HttpExchange xchg ) {
        try {
            writeResponse(xchg, HttpURLConnection.HTTP_OK, "");
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(xchg, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void writeResponse( HttpExchange xchg, int errorCode, String response ) {
        try {
            byte[] b = response.getBytes(ENCODING);
            xchg.getResponseHeaders().set("Content-Type", "application/json");
            xchg.sendResponseHeaders( errorCode, b.length);
            xchg.getResponseBody().write( b);
        } catch (Exception e) {
            logger.log(Level.INFO, "Unable to send response", e);
        }
    }

    private void writeResponse( HttpExchange xchg, int errorCode ) {
        try {
            byte[] b = "Bad request".getBytes(ENCODING);
            xchg.getResponseHeaders().set("Content-Type", "application/json");
            xchg.sendResponseHeaders( errorCode, b.length);
            xchg.getResponseBody().write( b);
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
        AtomicInteger count = new AtomicInteger();
        e.setThreadFactory((r) ->{;
            return new Thread(g, r, String.format("http-%d", count.incrementAndGet()));
        });
        return e;
    }

    private static URI relative(HttpExchange xchg) throws IOException{
        try{
            return new URI(xchg.getHttpContext().getPath()).relativize(xchg.getRequestURI());
        } catch(URISyntaxException _x){
            throw new IOException(_x);
        }
    }

//	private static Map<String, String> parseQueryParameters(HttpExchange xchg) {
//		return parseJsonParameters(xchg.getRequestURI().getQuery());
//	}

    private static Map<String, String> parsePostParameters(HttpExchange xchg) throws IOException {
        // lets get post parameters
        ByteArrayOutputStream inbytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        InputStream in = xchg.getRequestBody();
        int pos;
        while ( (pos = in.read(buffer)) != -1 ) {
            inbytes.write(buffer, 0, pos);
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("HttpUtilityServer post parameters: %s", new String(inbytes.toByteArray(), ENCODING)));
        }

        // lets parse parameters string
        return parseJsonParameters(new String(inbytes.toByteArray(), ENCODING));
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
