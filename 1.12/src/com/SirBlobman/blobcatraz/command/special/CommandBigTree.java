package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class CommandBigTree extends PlayerCommand {
	public CommandBigTree() {super("bigtree", "<tree/spruce/jungle>", "blobcatraz.special.bigtree", "4tree");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		TreeType tree;
		if(sub.equals("spruce")) tree = TreeType.TALL_REDWOOD;
		else if(sub.equals("tree")) tree = TreeType.BIG_TREE;
		else if(sub.equals("jungle")) tree = TreeType.JUNGLE;
		else if(sub.equals("dark_oak")) tree = TreeType.DARK_OAK;
		else {
			String error = prefix + getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
			return;
		}
		
		Location l = PlayerUtil.lookLocation(p);
		int y = l.getBlockX() + 1;
		l.setY(y);
		
		World w = l.getWorld();
		boolean s = w.generateTree(l, tree);
		if(s) {
			String msg = Util.format(prefix + "Successfully generated a tree of type '%1s'", tree.name());
			p.sendMessage(msg);
		} else {
			String error = prefix + "Failed to generate a tree! Make sure there is grass.";
			p.sendMessage(error);
		}
	}
}