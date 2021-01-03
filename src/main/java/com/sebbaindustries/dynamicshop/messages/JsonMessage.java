package com.sebbaindustries.dynamicshop.messages;

import com.google.gson.JsonObject;
import com.sebbaindustries.dynamicshop.utils.Color;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;

public class JsonMessage implements IMessage {

    public JsonMessage(String jsonString, CommandSender sender) {
        this.sender = sender;
        JsonObject object = ObjectUtils.getJsonString(jsonString);
        try {
            message = object.get("message").getAsString();
        } catch (NullPointerException e) {
            message = "$NULL";
        }

        try {
            hoverMessage = object.get("onHover").getAsString();
        } catch (NullPointerException e) {
            hoverMessage = null;
        }

        try {
            clickMessage = object.get("onClick").getAsString();
        } catch (NullPointerException e) {
            clickMessage = null;
        }

        try {
            String clickObjectText = object.get("clickAction").getAsString();
            switch (clickObjectText.toUpperCase()) {
                case "OPEN_URL", "URL" -> clickAction = ClickEvent.Action.OPEN_URL;
                case "COPY_TO_CLIPBOARD", "COPY" -> clickAction = ClickEvent.Action.COPY_TO_CLIPBOARD;
                case "RUN_COMMAND", "COMMAND" -> clickAction = ClickEvent.Action.RUN_COMMAND;
                case "SUGGEST_COMMAND", "SUGGEST" -> clickAction = ClickEvent.Action.SUGGEST_COMMAND;
                default -> clickAction = null;
            }
        } catch (NullPointerException e) {
            clickAction = null;
        }

    }

    private final CommandSender sender;
    private String message = null;
    private String hoverMessage = null;
    private String clickMessage = null;

    private final HoverEvent.Action hoverAction = HoverEvent.Action.SHOW_TEXT;
    private ClickEvent.Action clickAction;

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
        TextComponent msg = new TextComponent(Color.format(message));

        if (hoverMessage != null) {
            msg.setHoverEvent(new HoverEvent(hoverAction, new Text(Color.format(hoverMessage))));
        }
        if (clickMessage != null && clickAction != null) {
            msg.setClickEvent(new ClickEvent(clickAction, Color.format(clickMessage)));
        }
        sender.sendMessage(msg);
    }


    //public void send(Player player) {
    //    TextComponent msg = new TextComponent("&6Your Text");
//
    //    msg.setHoverEvent(new HoverEvent(HoverEvent.Action., new Text("Visit the Spigot website!")));
    //    msg.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "/yourcommand"));
    //    player.sendMessage(msg);
    //}

}