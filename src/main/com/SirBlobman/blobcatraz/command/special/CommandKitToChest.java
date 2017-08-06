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

public class CommandKitToChest extends PlayerCommand {
	public CommandKitToChest() {super("kittochest", "<name>", "blobcatraz.player.kit");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		String perm = Util.format("blobcatraz.player.kits.%1s", name);
		if(p.hasPermission(perm)) {
			Block b = PlayerUtil.lookBlock(p);
			BlockState bs = b.getState();
			if(bs instanceof Chest) {
				Chest c = (Chest) bs;
				ConfigKits.kitToChest(name, c);
				Location l = c.getLocation();
				String msg = Util.format(prefix + "The kit '%1s' was placed in the chest at \n%2s", name, Util.str(l));
				p.sendMessage(msg);
			}
		} else {
			String error = Util.format(prefix + Language.NO_PERMISSION, perm);
			p.sendMessage(error);
		}
	}
}