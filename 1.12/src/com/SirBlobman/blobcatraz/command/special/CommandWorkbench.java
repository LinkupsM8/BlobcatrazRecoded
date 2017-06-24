package com.SirBlobman.blobcatraz.command.special;

import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

public class CommandWorkbench extends PlayerCommand {
	public CommandWorkbench() {super("workbench", "", "blobcatraz.staff.workbench", "craftingtable", "craft", "wb", "table");}
	
	@Override
	public void run(Player p, String[] args) {
		p.openWorkbench(null, true);
		String msg = prefix + "Now opening your workbench...";
		p.sendMessage(msg);
	}
}