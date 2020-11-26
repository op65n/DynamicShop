package com.sebbaindustries.dynamicshop.engine.extensions;

import com.sebbaindustries.dynamicshop.Core;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExtPlayer {

    public Player player;
    String debugPlayerName = ":?";

    public ExtPlayer(Player player) {
        this.player = player;
        this.debugPlayerName = player.getName();
    }

    public ExtPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Core.gCore().logWarn("Sender is not instance of player!");
            return;
        }
        this.player = (Player) sender;
        this.debugPlayerName = player.getName();
    }

    public void sendMessage(String message) {
        try {
            if (player == null || !player.isOnline()) {
                Core.gCore().logWarn("Null player in ExtPlayer class. Player IGN: " + debugPlayerName);
                return;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
        player.sendMessage(com.sebbaindustries.dynamicshop.utils.Color.format(message));
    }

    public Double getBalance() {
        // TODO: Add Essentials money balance dependency
        return 0.00;
    }

}
