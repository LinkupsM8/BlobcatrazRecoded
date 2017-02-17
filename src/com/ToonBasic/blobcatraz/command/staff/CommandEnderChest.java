package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandEnderChest extends ICommand {
    public CommandEnderChest() {super("enderchest", "", "blobcatraz.staff.echest", "echest");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        Inventory chest = p.getEnderChest();
        p.openInventory(chest);
        p.sendMessage("Opening enderchest...");
    }
}
