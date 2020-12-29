package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
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

    private enum Recipient {
        CONSOLE,
        PLAYER,
        ;
    }

    private MessageType type;
    private Recipient recipient;
    private List<String> multilineMessage = new ArrayList<>();
    private String message = "$NULL";

    private Player player;
    private CommandSender sender;

    /**
     * Creates a new MessageBuilder instance and saves player to the memory
     *
     * @param player Player
     * @return MessageBuilder Instance
     */
    public static MessageBuilder sendTo(Player player) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.player = player;
        messageBuilder.recipient = Recipient.PLAYER;
        return messageBuilder;
    }

    /**
     * Creates a new MessageBuilder instance and saves command sender to the memory
     *
     * @param sender Command sender, player/console
     * @return MessageBuilder Instance
     */
    public static MessageBuilder sendTo(CommandSender sender) {
        MessageBuilder messageBuilder = new MessageBuilder();
        messageBuilder.sender = sender;
        messageBuilder.recipient = Recipient.CONSOLE;
        return messageBuilder;
    }

    /**
     * Adds text to a message and sets component type to single line
     *
     * @param message Message string
     * @return new MessageBuilderComponents class
     */
    public MessageBuilderComponents text(String message) {
        type = MessageType.NORMAL;
        this.message = message;
        return new MessageBuilderComponents();
    }

    /**
     * Adds text to a message and sets component type to multi line
     *
     * @param multilineMessage List of messages
     * @return new MessageBuilderComponents class
     */
    public MessageBuilderComponents text(List<String> multilineMessage) {
        type = MessageType.MULTILINE;
        this.multilineMessage = multilineMessage;
        return new MessageBuilderComponents();
    }

    /**
     * Sends player a message from the buffer
     */
    public void send() {
        /*
        MULTILINE message handling
         */
        if (type == MessageType.MULTILINE) {
            if (recipient == Recipient.PLAYER) {
                multilineMessage.forEach(player::sendMessage);
                return;
            }
            multilineMessage.forEach(sender::sendMessage);
            return;
        }

        /*
        SINGLE LINE message handling
         */
        if (recipient == Recipient.PLAYER) {
            player.sendMessage(message);
            return;
        }
        sender.sendMessage(message);
    }

    /**
     * @author SebbaIndustries
     * @version 1.0
     */
    public class MessageBuilderComponents {

        /**
         * Replaces all placeholders in a message string
         *
         * @param placeholder Placeholder enum
         * @param replacement Replacement String
         */
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

        /**
         * Placeholder component for double values
         *
         * @param placeholder Placeholder enum
         * @param replacement Replacement String
         * @return instance of MessageBuilderComponents class
         */
        public MessageBuilder.MessageBuilderComponents placeholder(Placeholder placeholder, double replacement) {
            placeholderImpl(placeholder, String.valueOf(replacement));
            return this;
        }

        /**
         * Placeholder component for int values
         *
         * @param placeholder Placeholder enum
         * @param replacement Replacement String
         * @return instance of MessageBuilderComponents class
         */
        public MessageBuilder.MessageBuilderComponents placeholder(Placeholder placeholder, int replacement) {
            placeholderImpl(placeholder, String.valueOf(replacement));
            return this;
        }

        /**
         * Placeholder component for string values
         *
         * @param placeholder Placeholder enum
         * @param replacement Replacement String
         * @return instance of MessageBuilderComponents class
         */
        public MessageBuilder.MessageBuilderComponents placeholder(Placeholder placeholder, String replacement) {
            placeholderImpl(placeholder, replacement);
            return this;
        }

        /**
         * Replaces all common placeholders
         *
         * @return instance of MessageBuilderComponents class
         */
        public MessageBuilder.MessageBuilderComponents applyCommonPlaceholders() {
            if (recipient == Recipient.CONSOLE) {
                Core.pluginLogger.logWarn("Console cannot use applyCommonPlaceholders method!");
                return this;
            }
            Arrays.stream(Placeholder.values()).forEach(placeholder -> {
                if (!placeholder.isCommon) return;
                switch (placeholder) {
                    case PLAYER -> placeholderImpl(Placeholder.PLAYER, player.getName());
                }
            });
            return this;
        }

        /**
         * Formats a message with color.
         *
         * @return instance of MessageBuilderComponents class
         */
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

        /**
         * Build the message
         *
         * @return MessageBuilder base class instance
         */
        public MessageBuilder build() {
            return MessageBuilder.this;
        }

    }


}
