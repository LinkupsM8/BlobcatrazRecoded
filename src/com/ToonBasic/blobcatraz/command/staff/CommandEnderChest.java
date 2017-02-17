package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

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
