package com.playtech.ptargame3.server.session;


public interface ClientListener {

    void clientConnected(String clientId, int userId);
    void clientDisconnected(String clientId, int userId);

}
