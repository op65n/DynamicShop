package com.sebbaindustries.dynamicshop.utils;

import net.minecraft.server.v1_16_R2.EntityPlayer;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;

public class CPlayer extends CraftPlayer {

    EntityPlayer entityPlayer;


    public CPlayer(CraftServer server, EntityPlayer entity) {
        super(server, entity);
        this.entityPlayer = entity;
    }

    @Override
    public void sendMessage(String message) {
        super.sendMessage(Color.format(message));
    }
}
