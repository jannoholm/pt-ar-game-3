package server.message.lobby;

import common.util.StringUtil;

import java.nio.ByteBuffer;

public class GetDetailedGameInfoRider {

    private String clientId;
    private String name;
    private byte team;
    private byte positionInTeam;

    public void parse(ByteBuffer messageData) {
        // make sure we read our needed bytes and all our expected bytes
        int byteCount = messageData.getInt();
        byte[] bytes = new byte[byteCount];
        messageData.get(bytes);
        messageData = ByteBuffer.wrap(bytes);

        // read structure data
        clientId = StringUtil.readUTF8String(messageData);
        name = StringUtil.readUTF8String(messageData);
        team = messageData.get();
        positionInTeam = messageData.get();
    }

    public void format(ByteBuffer messageData) {
        // remember old positionInTeam
        int position = messageData.position();
        // reserve space, but set some arbitrary value
        messageData.putInt(0);

        StringUtil.writeUTF8String(clientId, messageData);
        StringUtil.writeUTF8String(name, messageData);
        messageData.putInt(team);
        messageData.putInt(positionInTeam);

        // fix length
        messageData.putInt(position, messageData.position()-position-4);
    }

    protected void toStringImpl(StringBuilder s) {
        s.append("clientId=").append(getClientId());
        s.append(", name=").append(getName());
        s.append(", team=").append(getTeam());
        s.append(", position=").append(getPositionInTeam());
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getTeam() {
        return team;
    }

    public void setTeam(byte team) {
        this.team = team;
    }

    public int getPositionInTeam() {
        return positionInTeam;
    }

    public void setPositionInTeam(byte positionInTeam) {
        this.positionInTeam = positionInTeam;
    }
}
