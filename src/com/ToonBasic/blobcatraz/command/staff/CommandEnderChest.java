package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;

@PlayerOnly
public class CommandEnderChest extends ICommand {
    public CommandEnderChest() {super("enderchest", "", "blobcatraz.staff.echest", "echest");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        if(args.length > 0 && p.hasPermission("blobcatraz.staff.echest.other")) {
        	String target = args[0];
        	Player t = Bukkit.getPlayer(target);
        	if(t != null) {
        		Inventory chest = t.getEnderChest();
        		p.openInventory(chest);
        		p.sendMessage("Opening " + PlayerUtil.possesive(t.getName()) + " enderchest...");
        	} else {
        		String error = prefix + Language.INVALID_TARGET;
        		p.sendMessage(error);
        	}
        } else {
            Inventory chest = p.getEnderChest();
            p.openInventory(chest);
            p.sendMessage("Opening enderchest...");
        }
    }
}
