package com.sebbaindustries.dynamicshop.commands.actions;

import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.utils.Color;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DynamicShop extends CommandFactory implements ICmd, ITab {

    @Override
    public String command() {
        return "dynshop";
    }

    @Override
    public String permission() {
        return null;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {

    }

    private void info(CommandSender sender) {
        sender.sendMessage(Color.format("#<333333>[#<007070>Dynamic#<A60050>Shop#<333333>]"));
        sender.sendMessage(Color.format("#<333333>-------------"));
        sender.sendMessage(Color.format("#<007070>Version#<333333>: #<A60050>DynamicShop 0.1.7 (Leptir) for Paper 1.16.x"));
        sender.sendMessage(Color.format("#<007070>Plugin engine#<333333>: #<A60050>1.0.3-2-non-modular"));
        sender.sendMessage(Color.format("#<007070>Uptime#<333333>: #<A60050>${DAYS} days, ${HOURS} hours, ${MINUTES} min"));
        sender.sendMessage(Color.format(""));
        sender.sendMessage(Color.format("#<007070>Made by#<333333>: #<A60050>SebbaIndustries #<333333>/ #<A60050>Nzd_1"));
        sender.sendMessage(Color.format(""));
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
