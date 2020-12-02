package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private enum MessageType {
        NORMAL,
        MULTILINE,
        ;
    }

    private MessageType type;
    private List<String> multilineMessage = new ArrayList<>();
    private String message = "$NULL";

    private Player player;

    public static MessageBuilder sendTo(Player player) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.player = player;
        return messageBuilder;
    }

    public MessageBuilderComponents text(String message) {
        type = MessageType.NORMAL;
        this.message = message;
        return new MessageBuilderComponents();
    }

    public MessageBuilderComponents text(List<String> multilineMessage) {
        type = MessageType.MULTILINE;
        this.multilineMessage = multilineMessage;
        return new MessageBuilderComponents();
    }

    public void send() {
        if (type == MessageType.MULTILINE) {
            multilineMessage.forEach(player::sendMessage);
            return;
        }
        player.sendMessage(message);
    }

    public class MessageBuilderComponents {

        private void placeholderImpl(Placeholder placeholder, String replacement) {
            if (type == MessageType.MULTILINE) {
                int tail = 0;
                List<String> copy = multilineMessage;
                for (String line : copy) {
                    multilineMessage.set(tail, line.replaceAll(placeholder.get, replacement));
                    tail++;
                }
                return;
            }
            message = message.replaceAll(placeholder.get, replacement);
        }

        public MessageBuilder.MessageBuilderComponents placeholder(Placeholder placeholder, double replacement) {
            placeholderImpl(placeholder, String.valueOf(replacement));
            return this;
        }

        public MessageBuilder.MessageBuilderComponents placeholder(Placeholder placeholder, int replacement) {
            placeholderImpl(placeholder, String.valueOf(replacement));
            return this;
        }

        public MessageBuilder.MessageBuilderComponents placeholder(Placeholder placeholder, String replacement) {
            placeholderImpl(placeholder, replacement);
            return this;
        }

        public MessageBuilder.MessageBuilderComponents applyCommonPlaceholders() {
            Arrays.stream(Placeholder.values()).forEach(placeholder -> {
                if (!placeholder.isCommon) return;
                switch (placeholder) {
                    case PLAYER -> placeholderImpl(Placeholder.PLAYER, player.getName());
                }
            });
            return this;
        }

        public MessageBuilder.MessageBuilderComponents format() {
            if (type == MessageType.MULTILINE) {
                int tail = 0;
                List<String> copy = multilineMessage;
                for (String line : copy) {
                    multilineMessage.set(tail, Color.format(line));
                    tail++;
                }
                return this;
            }
            message = Color.format(message);
            return this;
        }

        public MessageBuilder build() {
            return MessageBuilder.this;
        }

    }


}
