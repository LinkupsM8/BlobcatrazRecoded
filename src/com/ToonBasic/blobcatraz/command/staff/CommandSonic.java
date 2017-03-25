package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.listener.ListenSonic;

@PlayerOnly
public class CommandSonic extends ICommand {
	public CommandSonic() {super("sonic", "", "blobcatraz.staff.sonic", "screwdriver", "doctorwho");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		ItemStack sonic = ListenSonic.sonic();
		pi.addItem(sonic);
		p.sendMessage("Try to find the TARDIS now!");
	}
}