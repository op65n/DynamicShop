package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.command.CommandSender;

public class TextMessage implements IMessage {
    
    private String message;
    private final CommandSender sender;

    public TextMessage(String message, CommandSender sender) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public IMessage applyCommonPlaceholders() {
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, String replacement) {
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, Integer replacement) {
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, Double replacement) {
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, Boolean replacement) {
        return this;
    }

    @Override
    public void send() {
        sender.sendMessage(Color.format(message));
    }
}
