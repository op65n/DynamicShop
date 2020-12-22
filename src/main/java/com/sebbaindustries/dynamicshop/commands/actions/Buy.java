package com.sebbaindustries.dynamicshop.commands.actions;

import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.messages.MessageBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class Buy extends CommandFactory implements ICmd, ITab {

    @Override
    public String command() {
        return "buy";
    }

    @Override
    public String permission() {
        return null;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            MessageBuilder.sendTo(sender).text(Message.get().consoleCannotExecute).format().build().send();
            return;
        }
        Player player = (Player) sender;
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
