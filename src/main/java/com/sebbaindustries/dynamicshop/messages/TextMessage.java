package com.sebbaindustries.dynamicshop.messages;

import com.sebbaindustries.dynamicshop.utils.Color;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TextMessage implements IMessage {

    private final CommandSender sender;
    private String message;

    public TextMessage(String message, CommandSender sender) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public IMessage applyCommonPlaceholders() {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            message = message.replaceAll("%player%", player.getName());
            message = message.replaceAll("%player_display%", player.getDisplayName());
            message = message.replaceAll("%player_balance%", "$NULL");
        }
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, String replacement) {
        message = message.replaceAll(placeholder, replacement);
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, Integer replacement) {
        message = message.replaceAll(placeholder, String.valueOf(replacement));
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, Double replacement) {
        message = message.replaceAll(placeholder, String.valueOf(replacement));
        return this;
    }

    @Override
    public IMessage placeholder(String placeholder, Boolean replacement) {
        message = message.replaceAll(placeholder, String.valueOf(replacement));
        return this;
    }

    @Override
    public void send() {
        if (sender instanceof Player) {
            sender.sendMessage(Color.format(message));
            return;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
