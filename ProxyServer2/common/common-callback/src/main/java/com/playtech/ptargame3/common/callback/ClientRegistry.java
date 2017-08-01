package com.playtech.ptargame3.common.callback;


import com.playtech.ptargame3.common.session.Session;

import java.util.Collection;

public interface ClientRegistry {
    Collection<Session> getClientSession(String clientId);
}
