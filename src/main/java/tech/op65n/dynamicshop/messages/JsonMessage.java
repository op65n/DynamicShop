package tech.op65n.dynamicshop.messages;

import com.google.gson.JsonObject;
import tech.op65n.dynamicshop.utils.Color;
import tech.op65n.dynamicshop.utils.ObjectUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JsonMessage implements IMessage {

    private final CommandSender sender;
    private final HoverEvent.Action hoverAction = HoverEvent.Action.SHOW_TEXT;
    private String message = null;
    private String hoverMessage = null;
    private String clickMessage = null;
    private ClickEvent.Action clickAction;
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
        TextComponent msg = new TextComponent(Color.format(message));

        if (hoverMessage != null) {
            msg.setHoverEvent(new HoverEvent(hoverAction, new Text(Color.format(hoverMessage))));
        }
        if (clickMessage != null && clickAction != null) {
            msg.setClickEvent(new ClickEvent(clickAction, Color.format(clickMessage)));
        }
        sender.sendMessage(msg);
    }

}
