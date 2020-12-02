package com.sebbaindustries.dynamicshop.commands.actions;

import com.moandjiezana.toml.TomlWriter;
import com.sebbaindustries.dynamicshop.Core;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.engine.extensions.ExtItemStack;
import com.sebbaindustries.dynamicshop.engine.extensions.ExtPlayer;
import com.sebbaindustries.dynamicshop.messages.Message;
import com.sebbaindustries.dynamicshop.messages.MessageBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AdminShop extends CommandFactory implements ICmd, ITab {

    @Override
    public String command() {
        return "adminshop";
    }

    @Override
    public String permission() {
        return null;
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            // TODO: Add no console message
            return;
        }

        Player player = (Player) sender;
        int amount = 7;
        String material = "some shit";
        double buyPrice = 4.0;
        double sellPrice = 4.0;

        MessageBuilder.to(player).text(Message.get().shopSuccessfulTransaction)
                .placeholder(material, MessageBuilder.Placeholder.MATERIAL_NAME)
                .placeholder(amount, MessageBuilder.Placeholder.AMOUNT)
                .placeholder(buyPrice, MessageBuilder.Placeholder.PRICE_BUY)
                .placeholder(sellPrice, MessageBuilder.Placeholder.PRICE_SELL)
                .applyCommonPlaceholders().format().build()
                .send();
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
