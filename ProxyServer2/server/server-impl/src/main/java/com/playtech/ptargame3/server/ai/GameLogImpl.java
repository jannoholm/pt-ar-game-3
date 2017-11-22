package com.playtech.ptargame3.server.ai;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

public class GameLogImpl implements GameLog {

    private static final Logger logger = Logger.getLogger(GameLogImpl.class.getName());

    private ScheduledExecutorService executor;
    private ScheduledFuture maintenanceFuture;
    private static ObjectWriter writer = new ObjectMapper().writer();

    private Map<String, StreamHolder> streams = new HashMap<>();
    private ArrayList<GameLogRecord> queue = new ArrayList<>();

    public GameLogImpl(ScheduledExecutorService executor) {
        this.executor = executor;
    }

    public void init() {
        setupMaintenance();
    }

    @Override
    public void writeLog(GameLogRecord record) {
        synchronized (this) {
            if (queue.size() < 2000) {
                queue.add(record);
            } else {
                logger.log(Level.INFO, "Dropping game log.");
            }
        }
    }

    private void setupMaintenance() {
        if (maintenanceFuture == null) {
            maintenanceFuture = executor.scheduleAtFixedRate(() -> {
                List<GameLogRecord> todo = new ArrayList<>();
                synchronized (this) {
                    todo.addAll(queue);
                    queue.clear();
                }
                if (todo.size() > 0) {
                    int count = 0;
                    for (GameLogRecord record : todo) {
                        try {
                            StreamHolder stream = streams.get(record.getGameId());
                            if (stream == null) {
                                stream = new StreamHolder(record.getGameId());
                                streams.put(record.getGameId(), stream);
                            }
                            stream.write(writer.writeValueAsBytes(record));
                            count++;
                        } catch (IOException e) {
                            logger.log(Level.INFO, "Unable to format record. ", e);
                        }
                    }
                    logger.info("Game log written to files. Records: " + count);
                }
                for (StreamHolder stream : new ArrayList<>(streams.values())) {
                    if (stream.isIdle()) {
                        try {
                            stream.close();
                            logger.info("Game log stream closed " + stream.gameId);
                        } catch (IOException e) {
                            logger.log(Level.WARNING, "Unable to close stream " + stream.gameId, e);
                        } finally {
                            streams.remove(stream.gameId);
                        }
                    }
                }
            }, 1000, 1023, TimeUnit.MILLISECONDS);
        } else {
            throw new IllegalStateException("Already started callback handler.");
        }
    }

    private static class StreamHolder {
        final String gameId;
        OutputStream out;
        long lastWrite = System.currentTimeMillis();

        StreamHolder(String gameId) throws IOException {
            this.gameId = gameId;
            String dir = "gamelog";
            //noinspection ResultOfMethodCallIgnored
            new File(dir).mkdirs();
            String filename = dir + File.separator + gameId + ".gz";
            out = new GZIPOutputStream(new FileOutputStream(filename, true));
        }
        void write(byte[] data) throws IOException {
            out.write(data);
            out.write('\n');
        }
        boolean isIdle() {
            return lastWrite + 60000 < System.currentTimeMillis();
        }
        void close() throws IOException {
            out.close();
        }
    }
}
