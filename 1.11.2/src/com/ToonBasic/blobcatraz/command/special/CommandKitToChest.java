package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigKits;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandKitToChest extends ICommand {
	public CommandKitToChest() {super("kittochest", "<name>", "blobcatraz.player.kit.tochest");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String name = Util.finalArgs(0, args);
			String kperm = "blobcatraz.player.kits." + name;
			if(p.hasPermission(kperm)) {
				Block b = PlayerUtil.lookBlock(p);
				BlockState bs = b.getState();
				if(bs instanceof Chest) {
					Chest chest = (Chest) bs;
					ConfigKits.kitToChest(name, chest);
					int x = chest.getX();
					int y = chest.getY();
					int z = chest.getZ();
					String coords = x + " " + y + " " + z;
					String msg = Util.color(prefix + "The chets at &a" + coords + " &rwas filled with the kit &c" + name);
					p.sendMessage(msg);
				}
			}
		}
	}
}