package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WordUtil;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandSkull extends PlayerCommand {
	public CommandSkull() {super("skull", "<player>", "blobcatraz.special.skull", "head");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = args[0];
		String poss = WordUtil.possessive(name);
		String disp = Util.format("&f%1s Head", poss);
		ItemStack is = ItemUtil.newHead(name, disp);
		
		PlayerInventory pi = p.getInventory();
		pi.addItem(is);
		String msg = Util.format("You now have %1s", disp);
		p.sendMessage(msg);
	}
}