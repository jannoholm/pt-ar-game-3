package com.playtech.ptargame3.examplebot;

import com.playtech.ptargame3.common.callback.ClientRegistry;
import com.playtech.ptargame3.common.session.Session;

import java.util.Collection;

public class BotClientRegistry implements ClientRegistry, ConnectivityListener {
    @Override
    public Collection<Session> getClientSession(String clientId) {
        return null;
    }

    @Override
    public String getName(String clientId) {
        return null;
    }

    @Override
    public void clientDisconnected(String clientId) {
        // ConnectivityListener method. remove client
    }
}
