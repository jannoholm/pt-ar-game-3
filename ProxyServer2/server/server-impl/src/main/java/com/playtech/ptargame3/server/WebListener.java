package com.playtech.ptargame3.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.playtech.ptargame3.server.database.DatabaseAccess;
import com.playtech.ptargame3.server.database.model.EloRating;
import com.playtech.ptargame3.server.database.model.User;
import com.playtech.ptargame3.server.exception.SystemException;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import javax.xml.ws.http.HTTPException;

/**
 * Simple http server from core java.
 *
 * @author Janno
 */
public final class WebListener {
    private static final Logger logger = Logger.getLogger(WebListener.class.getName());
    private static final String CTX_LEADERBOARD = "/leaderboard";
    private static final String CTX_USER = "/player";
    private static final String CTX_HTML = "/html";
    private static final String CTX_SERVER = "/server";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_DELETE = "DELETE";
    private static final String ENCODING = "UTF-8";

    private static final String HTML_DIR = "html";

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

        HttpContext ctxHtml = s.createContext(CTX_HTML);
        ctxHtml.setHandler(this::handleExchange);

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

            switch(httpExchange.getHttpContext().getPath()){
                case CTX_SERVER:
                    processServer(httpExchange, path);
                    break;
                case CTX_LEADERBOARD:
                    processLeaderboard(httpExchange, path);
                    break;
                case CTX_USER:
                    processUser(httpExchange, path);
                    break;
                case CTX_HTML:
                    processHtml(httpExchange, path);
                    break;
            }

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

    private void processServer(HttpExchange httpExchange, String path) throws IOException {
        switch(path){
            case "status":
                checkRequestMethod(httpExchange, METHOD_GET);
                getStatusRequest(httpExchange);
                break;
            default:
                if (logger.isLoggable(Level.INFO)) {
                    logger.info(String.format("HttpUtilityServer Cannot serve uri: %s, invalid path %s", httpExchange.getRequestURI(), path));
                }
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0L);
                break;
        }
    }

    private void processLeaderboard(HttpExchange httpExchange, String path) throws IOException {
        if (METHOD_GET.equals(httpExchange.getRequestMethod()) && path.trim().length() == 0) {
            // get players
            listLeaderboard(httpExchange);
        } else if (METHOD_GET.equals(httpExchange.getRequestMethod())) {
            // get player
            getUserRating(httpExchange, path);
        }
    }

    private void processUser(HttpExchange httpExchange, String path) throws IOException {
        if (METHOD_POST.equals(httpExchange.getRequestMethod()) && path.trim().length() == 0) {
            // create player
            createUserRequest(httpExchange);
        } else if (METHOD_POST.equals(httpExchange.getRequestMethod())) {
            // update player
            updateUserRequest(httpExchange, path);
        } else if (METHOD_DELETE.equals(httpExchange.getRequestMethod())) {
            // delete player
            deleteUserRequest(httpExchange, path);
        } else if (METHOD_GET.equals(httpExchange.getRequestMethod()) && path.trim().length() == 0) {
            // get players
            listUsersRequest(httpExchange);
        } else if (METHOD_GET.equals(httpExchange.getRequestMethod())) {
            // get player
            getUserRequest(httpExchange, path);
        }
    }

    private void processHtml(HttpExchange httpExchange, String path) throws IOException {
        File file = new File(HTML_DIR + File.separator + path);
        if (file.exists()) {
            byte[] b;
            try (FileInputStream in = new FileInputStream(file)) {
                b = new byte[in.available()];
                in.read(b);
            }
            httpExchange.getResponseHeaders().set("Content-Type", Files.probeContentType(file.toPath()) + "; charset=utf-8");
            httpExchange.sendResponseHeaders( HttpURLConnection.HTTP_OK, b.length);
            httpExchange.getResponseBody().write(b);
        } else {
            writeResponse(httpExchange, HttpURLConnection.HTTP_NOT_FOUND);
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

    private void getUserRequest( HttpExchange httpExchange, String idString ) {
        try {
            // get user
            int id = Integer.valueOf(idString);
            User user = databaseAccess.getUserDatabase().getUser(id);
            if (user == null || user.isHidden()) throw new HTTPException(HttpURLConnection.HTTP_NOT_FOUND);
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, new UserWrapper(user));
        } catch (HTTPException e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, e.getStatusCode());
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
            User user = databaseAccess.getUserDatabase().addUser(name, email);
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, new UserWrapper(user));
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void updateUserRequest( HttpExchange httpExchange, String idString ) {
        try {
            Map<String, String> params = parsePostParameters(httpExchange);

            // get user
            int id = Integer.valueOf(idString);
            User user = databaseAccess.getUserDatabase().getUser(id);

            // validate
            if (user == null || user.isHidden()) throw new HTTPException(HttpURLConnection.HTTP_NOT_FOUND);

            // create updated user
            String name = params.get("name");
            String email = params.get("email");
            user = new User(
                    id,
                    name == null ? user.getName() : name,
                    email == null ? user.getEmail() : email,
                    user.isHidden()
            );

            // update
            databaseAccess.getUserDatabase().updateUser(user);

            // send response
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, new UserWrapper(user));
        } catch (HTTPException e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, e.getStatusCode());
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void deleteUserRequest( HttpExchange httpExchange, String idString ) {
        try {
            int id = Integer.valueOf(idString);
            User user = databaseAccess.getUserDatabase().getUser(id);

            // validate
            if (user == null || user.isHidden()) throw new HTTPException(HttpURLConnection.HTTP_NOT_FOUND);

            // update hidden
            user = new User(user.getId(), user.getName(), user.getEmail(), true);

            // update
            databaseAccess.getUserDatabase().updateUser(user);

            // response
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, "OK");
        } catch (HTTPException e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, e.getStatusCode());
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void listUsersRequest( HttpExchange httpExchange ) {
        try {
            Collection<User> users = databaseAccess.getUserDatabase().getUsers();
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, convertUsers(users));
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void listLeaderboard( HttpExchange httpExchange ) {
        try {
            Collection<EloRating> leaderboard = databaseAccess.getRatingDatabase().getLeaderboard();
            Collection<User> users = databaseAccess.getUserDatabase().getUsers();
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, convertLeaderboard(leaderboard, users));
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void getUserRating( HttpExchange httpExchange, String idString ) {
        try {
            // get user
            int id = Integer.valueOf(idString);
            EloRating rating = databaseAccess.getRatingDatabase().getRating(id);
            if (rating.getMatches() == 0) throw new HTTPException(HttpURLConnection.HTTP_NOT_FOUND);
            writeResponse(httpExchange, HttpURLConnection.HTTP_OK, rating);
        } catch (HTTPException e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, e.getStatusCode());
        } catch (Exception e) {
            logger.log(Level.INFO, "Invalid request", e);
            writeResponse(httpExchange, HttpURLConnection.HTTP_BAD_REQUEST);
        }
    }

    private void writeResponse( HttpExchange httpExchange, int errorCode, Object response ) {
        try {
            ResponseWrapper rw = new ResponseWrapper(response);
            byte[] b = rw.getBytes();
            httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            httpExchange.sendResponseHeaders( errorCode, b.length);
            httpExchange.getResponseBody().write( b);
        } catch (Exception e) {
            logger.log(Level.INFO, "Unable to send response", e);
        }
    }

    private void writeResponse( HttpExchange httpExchange, int errorCode ) {
        try {
            ResponseWrapper rw = new ResponseWrapper("Bad request");
            byte[] b = rw.getBytes();
            httpExchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
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

            try {
                queryParameters.put(key, URLDecoder.decode(value, ENCODING));
            } catch (UnsupportedEncodingException e) {
                throw new SystemException("Unable to decode: " + value, e);
            }
        }
        if (logger.isLoggable(Level.INFO)) {
            logger.info(String.format("HttpUtilityServer uri parameters: %s", queryParameters));
        }
        return queryParameters;
    }

    private Collection<UserWrapper> convertUsers(Collection<User> users) {
        ArrayList<UserWrapper> wrapped = new ArrayList<>();
        for (User user : users) {
            if (!user.isHidden()) {
                wrapped.add(new UserWrapper(user));
            }
        }
        return wrapped;
    }

    private Collection<LeaderboardWrapper> convertLeaderboard(Collection<EloRating> leaderboard, Collection<User> users) {
        ArrayList<LeaderboardWrapper> wrapped = new ArrayList<>();
        for (EloRating rating : leaderboard) {
            for (User user : users) {
                if (user.getId() == rating.getUserId()) {
                    LeaderboardWrapper wrapper = new LeaderboardWrapper(user.getName(), rating);
                    wrapped.add(wrapper);
                }
            }
        }
        return wrapped;
    }

    private static class ResponseWrapper {
        private static ObjectWriter writer = new ObjectMapper().writer();
        private Object data;

        private ResponseWrapper( Object data ) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        private byte[] getBytes() {
            try {
                return writer.writeValueAsBytes(this);
            } catch (IOException e) {
                throw new SystemException("Unable to serialize object: " + data, e);
            }
        }
    }

    private static class UserWrapper {
        private final int id;
        private final String name;
        private final String email;
        UserWrapper(User user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

    private static class LeaderboardWrapper {
        private final String name;
        private final int userId;
        private final int eloRating;
        private final int matches;
        private final int goals;
        private final int bulletHits;
        private final int totalScore;
        private final int ballTouches;
        private final int boostTouches;

        public LeaderboardWrapper(String name, EloRating rating) {
            this.name = name;
            this.userId = rating.getUserId();
            this.eloRating = rating.getEloRating();
            this.matches = rating.getMatches();
            this.goals = rating.getGoals();
            this.bulletHits = rating.getBulletHits();
            this.totalScore = rating.getTotalScore();
            this.ballTouches = rating.getBallTouches();
            this.boostTouches = rating.getBoostTouches();
        }

        public String getName() {
            return name;
        }

        public int getUserId() {
            return userId;
        }

        public int getEloRating() {
            return eloRating;
        }

        public int getMatches() {
            return matches;
        }

        public int getGoals() {
            return goals;
        }

        public int getBulletHits() {
            return bulletHits;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public int getBallTouches() {
            return ballTouches;
        }

        public int getBoostTouches() {
            return boostTouches;
        }
    }

}
