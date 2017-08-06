package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandDeleteWarp extends PlayerCommand {
	public CommandDeleteWarp() {super("deletewarp", "<warp>", "blobcatraz.staff.deletewarp", "delwarp", "removewarp", "remwarp");}
	
	@Override
	public void run(Player p, String[] args) {
		String warp = Util.finalArgs(0, args);
		ConfigWarps.delete(warp);
		String msg = Util.format(prefix + "You deleted a warp called &a%s&r.", warp);
		p.sendMessage(msg);
	}
}