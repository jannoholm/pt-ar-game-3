package common.callback;


import common.session.Session;

import java.util.Collection;

public interface ClientRegistry {
    Collection<Session> getClientSession(String clientId);
}
