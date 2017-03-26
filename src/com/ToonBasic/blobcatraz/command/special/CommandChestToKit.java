package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigKits;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandChestToKit extends ICommand {
	public CommandChestToKit() {super("chesttokit", "<name>", "blobcatraz.player.kit.fromchest");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String name = Util.finalArgs(0, args);
			Block b = PlayerUtil.lookBlock(p);
			BlockState bs = b.getState();
			if(bs instanceof Chest) {
				Chest chest = (Chest) bs;
				Inventory i = chest.getInventory();
				ConfigKits.create(i, name);
				int x = chest.getX();
				int y = chest.getY();
				int z = chest.getZ();
				String coords = x + " " + y + " " + z;
				String msg = Util.color(prefix + "The chest at &5" + coords + "&r was turned into the kit &c" + name);
				p.sendMessage(msg);
			} else {
				String error = "You must look at a CHEST, not a §5" + b.getType().name();
				p.sendMessage(error);
			}
		}
	}
}