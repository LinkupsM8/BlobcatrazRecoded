package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CommandFurnace extends ICommand {
    public CommandFurnace() {
        super("furnace", "", "blobcatraz.staff.furnace", "smelt");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        Inventory furnace = Bukkit.createInventory(player, InventoryType.FURNACE);
        ((CraftPlayer) player).getHandle().openContainer(((CraftInventory) furnace).getInventory());
        player.sendMessage(prefix + "Now opening your furnace...");
    }
}
