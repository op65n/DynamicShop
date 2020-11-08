package com.sebbaindustries.dynamicshop.commands.components;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public interface ICmd {

    void execute(@NotNull CommandSender sender, @NotNull String[] args);

}
