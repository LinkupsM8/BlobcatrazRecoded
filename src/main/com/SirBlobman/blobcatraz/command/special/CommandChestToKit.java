package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandChestToKit extends PlayerCommand {
	public CommandChestToKit() {super("chesttokit", "<name>", "blobcatraz.staff.createkit", "ctk");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		Block b = PlayerUtil.lookBlock(p);
		BlockState bs = b.getState();
		if(bs instanceof Chest) {
			Chest c = (Chest) bs;
			Location l = c.getLocation();
			Inventory i = c.getInventory();
			ConfigKits.create(name, i);
			String msg = Util.format(prefix + "You created a kit called '%1s' from a chest at \n%2s", name, Util.str(l));
			p.sendMessage(msg);
		} else {
			String error = "You are not looking at a CHEST";
			p.sendMessage(error);
		}
	}
}