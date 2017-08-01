package com.playtech.ptargame3.server;


import com.playtech.ptargame3.common.callback.ClientRegistry;
import com.playtech.ptargame3.common.session.Session;
import com.playtech.ptargame3.common.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ProxyClientRegistry implements ClientRegistry {
    private Map<String, SessionHolder> sessions = new ConcurrentHashMap<>();

    private String generateClientId() {
        return UUID.randomUUID().toString();
    }
    public String addClientConnection(String clientId, String name, String email, Session session) {
        if (StringUtil.isNull(clientId)) {
            clientId = generateClientId();
        }

        SessionHolder holder = sessions.get(clientId);
        if (holder == null) {
            sessions.put(clientId, new SessionHolder(clientId, name, email, session));
        } else {
            holder.add(name, email, session);
        }

        return clientId;
    }
    public void removeClientConnection(String clientId, Session session) {
        if (StringUtil.isNull(clientId)) return;

        SessionHolder removed = sessions.get(clientId);
        removed.removeSession(session);
    }

    public Collection<Session> getClientSession(String clientId) {
        if (!StringUtil.isNull(clientId)) {
            SessionHolder holder = sessions.get(clientId);
            if (holder != null) {
                return holder.sessions;
            }
        }
        return Collections.emptyList();
    }

    private static class SessionHolder {
        private final String clientId;
        private final String name;
        private final String email;
        private Collection<Session> sessions;
        private long lastSeen;

        private SessionHolder(String clientId, String name, String email, Session session) {
            if (StringUtil.isNull(name)) {
                throw new IllegalArgumentException("Name is missing.");
            }
            if (StringUtil.isNull(email)) {
                throw new IllegalArgumentException("Email is missing.");
            }

            this.clientId = clientId;
            this.name = name;
            this.email = email;
            this.sessions = Collections.singletonList(session);
        }

        private void add(String name, String email, Session session) {
            if (!this.name.equals(name)) {
                throw new IllegalArgumentException("Invalid connection data! Name not matching: " + this.name + " vs. " + name);
            }
            if (!this.email.equals(email)) {
                throw new IllegalArgumentException("Invalid connection data! Email not matching: " + this.email + " vs. " + email);
            }
            synchronized (this) {
                ArrayList<Session> tmp = new ArrayList<>(this.sessions.size() + 1);
                tmp.addAll(this.sessions);
                tmp.add(session);
                this.sessions = Collections.unmodifiableCollection(tmp);
            }
        }

        private void removeSession(Session session) {
            synchronized (this) {
                ArrayList<Session> tmp = new ArrayList<>(this.sessions.size());
                for (Session s : this.sessions) {
                    if (s.getId() != session.getId()) {
                        tmp.add(s);
                    }
                }
                this.sessions = Collections.unmodifiableCollection(tmp);
            }
        }
    }
}
