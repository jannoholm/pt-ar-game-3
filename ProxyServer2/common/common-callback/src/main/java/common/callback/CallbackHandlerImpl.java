package common.callback;


import common.message.Message;
import common.message.MessageParser;
import common.session.Session;
import common.task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CallbackHandlerImpl implements CallbackHandler {

    public static final Logger logger = Logger.getLogger(CallbackHandlerImpl.class.getName());

    private static final int TIMEOUT = 5000;

    private final MessageParser messageParser;
    private final ClientRegistry clientRegistry;
    private final ScheduledExecutorService executor;

    private final Map<Long, CallbackHolder> callbackStore;
    private ScheduledFuture maintenanceFuture;

    public CallbackHandlerImpl(MessageParser messageParser, ClientRegistry clientRegistry, ScheduledExecutorService executor) {
        this.messageParser = messageParser;
        this.clientRegistry = clientRegistry;
        this.executor = executor;
        this.callbackStore = new LinkedHashMap<>(16, 0.75f, false);
    }

    public void start() {
        if (maintenanceFuture == null) {
            maintenanceFuture = executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    // filter out actual timeouts
                    ArrayList<CallbackHolder> timed = new ArrayList<>();
                    long current = System.currentTimeMillis();
                    synchronized (callbackStore) {
                        for (CallbackHolder holder : callbackStore.values()) {
                            if (holder.time + TIMEOUT < current) {
                                timed.add(holder);
                            } else {
                                break;
                            }
                        }
                    }

                    // do something with timed out things
                    for (CallbackHolder holder : timed) {
                        logger.log(Level.FINE, "Timeout triggered by callback handler: " + holder.task.getContext().getInput().getId());
                        setTaskContext(holder.task, holder.messageId, ResponseStatus.TIMEOUT);
                        holder.task.scheduleExecution();
                        removeFromCallbackStore(holder.messageId);
                    }
                }
            }, 500, 512, TimeUnit.MILLISECONDS);
        } else {
            throw new IllegalStateException("Already started callback handler.");
        }
    }

    public void stop() {
        maintenanceFuture.cancel(true);
    }

    @Override
    public void sendMessage(Message message) {
        Collection<Session> clientSessions = clientRegistry.getClientSession(message.getHeader().getClientId());
        for (Session session : clientSessions) {
            session.sendMessage(message);
        }
        if (clientSessions.isEmpty()) {
            logger.fine(()->"Message unroutable: " + message);
        }
    }

    @Override
    public void sendCallback(Task task, Message request) {
        setTaskContext(task, request.getHeader().getMessageId(), ResponseStatus.PENDING);
        addToCallbackStore(task, request);
        Collection<Session> clientSessions = clientRegistry.getClientSession(request.getHeader().getClientId());
        if (!clientSessions.isEmpty()) {
            for (Session session : clientSessions) {
                session.sendMessage(request);
            }
        } else {
            setTaskContext(task, request.getHeader().getMessageId(), ResponseStatus.UNROUTABLE);
            removeFromCallbackStore(request.getHeader().getMessageId());
            logger.fine(()->"Message unroutable: " + request);
        }
    }

    @Override
    public void sendCallback(Task task, Message request, Session session) {
        setTaskContext(task, request.getHeader().getMessageId(), ResponseStatus.PENDING);
        addToCallbackStore(task, request);
        session.sendMessage(request);
    }

    @Override
    public void addResponse(Message response) {
        CallbackHolder callback = getCallback(response.getHeader().getMessageId());
        if ( callback != null ) {
            setTaskContext(callback.task, response.getHeader().getMessageId(), ResponseStatus.SUCCESS, response);
            callback.task.scheduleExecution();
            removeFromCallbackStore(response.getHeader().getMessageId());
            logger.fine(()->"Adding response " + response.getHeader().getMessageId() + " to context: " + callback.task.getContext().getInput().getId() + " - time: " + (System.currentTimeMillis()-callback.time) + "ms");
        } else {
            logger.fine(()->"Discarding response: " + response);
        }
    }

    @Override
    public ResponseStatus getResponseStatus(Task task, Message message) {
        return (ResponseStatus)task.getContext().get(getContextIdForStatus(message.getHeader().getMessageId()));
    }

    @Override
    public Message getResponse(Task task, Message message) {
        return (Message)task.getContext().get(getContextIdForResponse(message.getHeader().getMessageId()));
    }

    private void addToCallbackStore(Task task, Message request) {
        synchronized (callbackStore) {
            callbackStore.put(request.getHeader().getMessageId(), new CallbackHolder(task, request.getHeader().getMessageId(), System.currentTimeMillis()));
        }
    }

    private CallbackHolder getCallback(Long messageId) {
        synchronized (callbackStore) {
            return callbackStore.get(messageId);
        }
    }

    private void removeFromCallbackStore(Long messageId) {
        synchronized (callbackStore) {
            callbackStore.remove(messageId);
        }
    }

    private void setTaskContext(Task task, Long messageId, CallbackHandler.ResponseStatus status) {
        setTaskContext(task, messageId, status, null);
    }

    private void setTaskContext(Task task, Long messageId, CallbackHandler.ResponseStatus status, Message response) {
        task.getContext().put(getContextIdForStatus(messageId), status);
        if (response != null) {
            task.getContext().put(getContextIdForResponse(messageId), response);
        }
    }

    private String getContextIdForStatus(Long messageId) {
        return "CBH-S-" + messageId;
    }

    private String getContextIdForResponse(Long messageId) {
        return "CBH-R-" + messageId;
    }

    private static class CallbackHolder {
        private final Task task;
        private final long messageId;
        private final long time;

        private CallbackHolder(Task task, long messageId, long time) {
            this.task = task;
            this.messageId = messageId;
            this.time = time;
        }
    }

}
