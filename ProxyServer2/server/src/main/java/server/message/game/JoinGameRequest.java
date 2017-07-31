package server.message.game;

import common.message.MessageHeader;
import common.util.StringUtil;
import server.message.AbstractRequest;

import java.nio.ByteBuffer;

public class JoinGameRequest extends AbstractRequest {

    private String gameId;
    private boolean watcher;

    public JoinGameRequest(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", gameId=").append(getGameId());
        s.append(", watcher=").append(isWatcher());
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        this.gameId = StringUtil.readUTF8String(messageData);
        this.watcher = messageData.get() != 0;
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(gameId, messageData);
        messageData.put((byte)(watcher ? 1 : 0));
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public boolean isWatcher() {
        return watcher;
    }

    public void setWatcher(boolean watcher) {
        this.watcher = watcher;
    }
}
