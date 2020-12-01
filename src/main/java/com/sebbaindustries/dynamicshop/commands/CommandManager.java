package com.sebbaindustries.dynamicshop.commands;

import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.actions.*;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author SebbaIndustries
 * @version 1.0
 */
public class CommandManager implements CommandExecutor, TabCompleter {

    private final Core core;
    private final List<CommandFactory> commands = new ArrayList<>();

    public CommandManager(Core core) {
        this.core = core;
        registerCommands(
                new Shop(),
                new Buy(),
                new Sell(),
                new Price(),
                new AdminShop()
        );
    }

    private void registerCommands(CommandFactory... newCommands) {
        Arrays.stream(newCommands).forEach(command -> {
            System.out.println(command.command());
            Objects.requireNonNull(core.getCommand(command.command())).setExecutor(this);
            Objects.requireNonNull(core.getCommand(command.command())).setTabCompleter(this);
            commands.add(command);
        });
    }

    /**
     * All afk commands execute here
     *
     * @param sender  Player/console instance
     * @param command No idea
     * @param label   alias
     * @param args    the good stuff
     * @return true
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        commands.forEach(cmd -> {
            if (cmd.command().equalsIgnoreCase(label)) {
                if (cmd.permission() == null) {
                    ((ICmd) cmd).execute(sender, args);
                    return;
                }
                if (!sender.hasPermission(cmd.permission())) {
                    //sender.sendMessage(Messages.get("no_permission"));
                    return;
                }
                ((ICmd) cmd).execute(sender, args);
            }
        });

        return true;
    }

    /**
     * Tab complete for all commands
     *
     * @param sender  player/console instance
     * @param command no idea
     * @param label   alias
     * @param args    the good stuff
     * @return list of completions or null->list of all online players
     */
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        for (CommandFactory cmd : commands) {
            if (cmd.command().equalsIgnoreCase(label)) {
                List<String> tab = ((ITab) cmd).complete(sender, args);
                return StringUtil.copyPartialMatches(args[args.length - 1], tab, new ArrayList<>(tab.size()));
            }
        }
        return null;
    }
}
