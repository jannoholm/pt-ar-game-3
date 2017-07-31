package server.message.lobby;

import common.message.MessageHeader;
import common.util.StringUtil;
import server.message.AbstractMessage;
import server.message.AbstractRequest;

import java.nio.ByteBuffer;

public class GetGamesRequest extends AbstractRequest {

    private String filter;
    private boolean all;

    public GetGamesRequest(MessageHeader header) {
        super(header);
    }

    public void parse(ByteBuffer messageData) {
        super.parse(messageData);
        filter = StringUtil.readUTF8String(messageData);
        all = (messageData.get() != 0);
    }

    public void format(ByteBuffer messageData) {
        super.format(messageData);
        StringUtil.writeUTF8String(filter, messageData);
        messageData.put((byte)(all ? 1 : 0));
    }

    protected void toStringImpl(StringBuilder s) {
        super.toStringImpl(s);
        s.append(", filter=").append(filter);
        s.append(", all=").append(all ? 1 : 0);
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }
}
