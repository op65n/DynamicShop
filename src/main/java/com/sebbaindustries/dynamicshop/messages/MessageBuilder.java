package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public class MessageBuilder {

    CommandSender sender;

    public MessageCreator recipient(CommandSender sender) {
        this.sender = sender;
        return new MessageCreator();
    }

    public class MessageCreator {
        public IMessage message(final String message) {
            if (ObjectUtils.isValidJson(message) && sender instanceof Player) return new JsonMessage(message, sender);
            return new TextMessage(message, sender);
        }
    }

}
