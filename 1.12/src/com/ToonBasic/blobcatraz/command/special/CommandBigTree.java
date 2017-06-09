package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;

@PlayerOnly
public class CommandBigTree extends ICommand {
	public CommandBigTree() {super("bigtree", "<type>", "blobcatraz.special.bigtree", "4tree");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String sub = args[0].toLowerCase();
		TreeType tree;
		if(sub.equals("spruce")) tree = TreeType.TALL_REDWOOD;
		else if(sub.equals("tree")) tree = TreeType.BIG_TREE;
		else if(sub.equals("jungle")) tree = TreeType.JUNGLE;
		else {
			String error = getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
			return;
		}
		
		Location l = PlayerUtil.lookLocation(p);
		l.setY(l.getY() + 1);
		World w = l.getWorld();
		boolean success = w.generateTree(l, tree);
		if(success) {
			String msg = prefix + "Successfully generate a tree of type '" + tree.name() + "'.";
			p.sendMessage(msg);
		} else {
			String error = prefix + "Failed to generate a tree!";
			p.sendMessage(error);
		}
	}
}