package tech.op65n.dynamicshop.messages;

public interface IMessage {

    static MessageBuilder builder() {
        return new MessageBuilder();
    }

    IMessage applyCommonPlaceholders();

    IMessage placeholder(String placeholder, String replacement);

    IMessage placeholder(String placeholder, Integer replacement);

    IMessage placeholder(String placeholder, Double replacement);

    IMessage placeholder(String placeholder, Boolean replacement);

    void send();

}
