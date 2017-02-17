package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
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
public class CommandEnderChest extends ICommand {
    public CommandEnderChest() {super("enderchest", "", "blobcatraz.staff.echest", "echest");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        Inventory echest = Bukkit.createInventory(player, InventoryType.ENDER_CHEST);
        player.openInventory(echest);
        player.sendMessage(prefix + "Now opening your enderchest...");
    }
}
