package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MessageBuilder {

    public enum Placeholder {
        PLAYER("%player%", true),
        BALANCE("%balance%", false),
        AMOUNT("%amount%", false),
        PRICE_BUY("%price_buy%", false),
        PRICE_SELL("%price_sell%", false),
        MATERIAL_NAME("%material%", false),
        ERROR("%error%", false),

        ;

        public String get;
        public boolean isCommon;

        Placeholder(String placeholder, boolean common) {
            this.get = placeholder;
            this.isCommon = common;
        }
    }

    private String message = "$NULL";
    private Player player;

    public static MessageBuilder to(Player player) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.player = player;
        return messageBuilder;
    }

    public MessageBuilderComponents text(String message) {
        this.message = message;
        return new MessageBuilderComponents();
    }

    public void send() {
        player.sendMessage(message);
    }

    public class MessageBuilderComponents {

        public MessageBuilderComponents placeholder(double replacement, Placeholder placeholder) {
            message = message.replaceAll(placeholder.get, String.valueOf(replacement));
            return this;
        }

        public MessageBuilderComponents placeholder(int replacement, Placeholder placeholder) {
            message = message.replaceAll(placeholder.get, String.valueOf(replacement));
            return this;
        }

        public MessageBuilderComponents placeholder(String replacement, Placeholder placeholder) {
            message = message.replaceAll(placeholder.get, replacement);
            return this;
        }

        public MessageBuilderComponents applyCommonPlaceholders() {
            Arrays.stream(Placeholder.values()).forEach(placeholder -> {
                if (!placeholder.isCommon) return;
                switch (placeholder) {
                    case PLAYER -> message = message.replaceAll(Placeholder.PLAYER.get, player.getName());
                }
            });
            return this;
        }

        public MessageBuilderComponents format() {
            message = Color.format(message);
            return this;
        }

        public MessageBuilder build() {
            return MessageBuilder.this;
        }

    }


}
