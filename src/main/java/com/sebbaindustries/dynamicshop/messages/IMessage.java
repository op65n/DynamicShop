package com.sebbaindustries.dynamicshop.messages;

import org.bukkit.entity.Player;

public interface IMessage {

    IMessage applyCommonPlaceholders();

    IMessage placeholder(String placeholder, String replacement);

    IMessage placeholder(String placeholder, Integer replacement);

    IMessage placeholder(String placeholder, Double replacement);

    IMessage placeholder(String placeholder, Boolean replacement);

    void send();

    static MessageBuilder builder() {
        return new MessageBuilder();
    }

}
