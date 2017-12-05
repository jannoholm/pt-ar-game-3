package com.playtech.ptargame3.examplebot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

public enum RuntimeInfo {
    INSTANCE;

    private static final Logger logger = Logger.getLogger(RuntimeInfo.class.getName());

    private String clientId;

    RuntimeInfo() {
        try {
            String LOCATION = "conf" + File.separator + "runtime";
            String PROP_CLIENT_ID = "clientId";
            File f = new File(LOCATION);
            Properties properties = new Properties();
            if (f.exists()) {
                properties.load(new FileInputStream(f));
                clientId = properties.getProperty(PROP_CLIENT_ID);
            }
            if (clientId == null) {
                clientId = UUID.randomUUID().toString();
            }
            properties.setProperty(PROP_CLIENT_ID, clientId);
            properties.store(new FileOutputStream(f), "Runtime properties");
        } catch (IOException e) {
            throw new SystemException("Unable to init bot.", e);
        }

    }

    public String getClientId() {
        return clientId;
    }
}
