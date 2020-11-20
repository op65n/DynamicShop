package com.sebbaindustries.dynamicshop.commands.actions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sebbaindustries.dynamicshop.commands.components.CommandFactory;
import com.sebbaindustries.dynamicshop.commands.components.ICmd;
import com.sebbaindustries.dynamicshop.commands.components.ITab;
import com.sebbaindustries.dynamicshop.utils.ObjectUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

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
        ItemStack iStack = new ItemStack(player.getInventory().getItemInOffHand());
        String name = iStack.getType().toString();
        ObjectUtils.saveGsonFile(name, iStack);

        ItemMeta itemMeta;
        JsonObject jObject = ObjectUtils.getJsonFromFile(name);
        if (jObject == null) {
            return;
        }
        JsonElement jElement = jObject.get("meta");
        itemMeta = ObjectUtils.getClassFromGson(jElement, ItemMeta.class);
        ObjectUtils.deserializeObjectToString(itemMeta);

        //ItemStack itemStack = new ItemStack(ObjectUtils.getGsonFile(name, ItemStack.class));
        //player.getInventory().setItemInMainHand(itemStack);
    }

    @Override
    public List<String> complete(@NotNull CommandSender sender, @NotNull String[] args) {
        return Collections.singletonList(" ");
    }
}
