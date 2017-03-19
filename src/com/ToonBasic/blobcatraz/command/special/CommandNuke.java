package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandNuke extends ICommand {
	public CommandNuke() {super("nuke", "<player>", "blobcatraz.special.nuke", "tntrain");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		if(args.length > 0) {
			String target = args[0];
			Player t = Bukkit.getPlayer(target);
			if(t != null) {
				World w = t.getWorld();
				Location l = t.getLocation();
				t.sendMessage("May you rest in peace...");
				for(int x = -10; x <= 10; x += 5) {
					for(int z = -10; z <= 10; z += 5) {
						Location loc = new Location(w, l.getBlockX() + x, w.getHighestBlockYAt(l) + 64, l.getZ() + z);
						TNTPrimed tnt = w.spawn(loc, TNTPrimed.class);
						tnt.setFuseTicks(100);
					}
				}
			} else {
				String msg = Language.INVALID_TARGET;
				cs.sendMessage(msg);
			}
		}
	}
}