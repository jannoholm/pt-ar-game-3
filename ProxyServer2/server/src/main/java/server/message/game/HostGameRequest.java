package server.message.game;

import common.message.MessageHeader;
import common.util.StringUtil;
import server.message.AbstractRequest;

import java.nio.ByteBuffer;

public class HostGameRequest extends AbstractRequest {

    private int players;
    private int aiPlayers;
    private String aiType;
    private boolean joinAsPlayer;

    public HostGameRequest(MessageHeader header) {
        super(header);
    }

    @Override
    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", players=").append(getPlayers());
        s.append(", aiPlayers=").append(getAiPlayers());
        s.append(", aiType=").append(getAiType());
        s.append(", join=").append(isJoinAsPlayer());
    }

    @Override
    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        this.players = messageData.getInt();
        this.aiPlayers = messageData.getInt();
        this.aiType = StringUtil.readUTF8String(messageData);
        this.joinAsPlayer = messageData.get() != 0;
    }

    @Override
    public void format(ByteBuffer messageData) {
        super.format(messageData);
        messageData.putInt(this.players);
        messageData.putInt(this.aiPlayers);
        StringUtil.writeUTF8String(aiType, messageData);
        messageData.put((byte)(joinAsPlayer ? 1 : 0));
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getAiPlayers() {
        return aiPlayers;
    }

    public void setAiPlayers(int aiPlayers) {
        this.aiPlayers = aiPlayers;
    }

    public String getAiType() {
        return aiType;
    }

    public void setAiType(String aiType) {
        this.aiType = aiType;
    }

    public boolean isJoinAsPlayer() {
        return joinAsPlayer;
    }

    public void setJoinAsPlayer(boolean joinAsPlayer) {
        this.joinAsPlayer = joinAsPlayer;
    }
}
