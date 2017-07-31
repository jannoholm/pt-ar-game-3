package common.message;

public interface MessageFactory {

    <T extends Message> T createMessage(MessageHeader messageHeader);

    <T extends Message> int getMessageType(Class<T> message);

}
