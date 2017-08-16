package com.playtech.ptargame3.server.session;


public interface ClientListener {

    void clientConnected(String clientId);
    void clientDisconnected(String clientId);

}
