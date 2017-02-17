package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandClearInventory extends ICommand {
    public CommandClearInventory() {
        super("clear", "[player]", "blobcatraz.staff.clearinv", "ci", "clearinventory");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
    	if(args.length > 0) {
    		String target = args[0];
    		Player p = Bukkit.getPlayer(target);
    		if(p != null) {
                PlayerInventory pi = p.getInventory();
                pi.clear();
                p.sendMessage(prefix + cs.getName() + " cleared your inventory!");
    		} else {
    			String error = Util.color(Language.INVALID_TARGET);
    			cs.sendMessage(error);
    		}
    	} else {
            Player p = (Player) cs;
            PlayerInventory pi = p.getInventory();
            pi.clear();
            p.sendMessage(prefix + "You have cleared your inventory!");
    	}
    }
}
