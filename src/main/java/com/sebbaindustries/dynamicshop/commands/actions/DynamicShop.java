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
        info(sender);
    }

    private void info(CommandSender sender) {
        sender.sendMessage(Color.format("#<333333>[#<007070>DynamicShop#<333333>]"));
        sender.sendMessage(Color.format(" "));
        sender.sendMessage(Color.format("&fVersion#<333333>: #<A60050>0.1.7 #<333333>(&fLeptir#<333333>) #<A60050>for &fPaper 1.16.x"));
        sender.sendMessage(Color.format("&fPlugin engine#<333333>: #<A60050>1.0.3-2-non-modular"));
        sender.sendMessage(Color.format("&fUptime#<333333>: &f3 #<A60050>days, &f8 #<A60050>hours, &f23 #<A60050>min"));
        sender.sendMessage(Color.format(""));
        sender.sendMessage(Color.format("&fMade by#<333333>: #<A60050>SebbaIndustries &f/ #<A60050>Nzd_1"));
        sender.sendMessage(Color.format(""));
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return null;
    }
}
