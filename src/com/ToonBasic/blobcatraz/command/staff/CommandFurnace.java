package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Plugin created by Lilith
 * MC-Market: http://www.mc-market.org/members/16269/
 * Spigot: https://www.spigotmc.org/members/razorkings.32987/
 * Redistribution is not permitted
 * If you find any errors, please send me a PM on either Spigot or MC-Market.
 */
public class CommandFurnace extends ICommand {
    public CommandFurnace() {
        super("furance", "", "blobcatraz.staff.furnace", "fur");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        Inventory furnace = Bukkit.createInventory(player, InventoryType.FURNACE);
        ((CraftPlayer) player).getHandle().openContainer(((CraftInventory) furnace).getInventory());
        player.sendMessage(prefix + "Now opening your furnace...");
    }
}
