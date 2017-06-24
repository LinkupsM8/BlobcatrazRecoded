package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ScoreboardUtil;

import org.bukkit.entity.Player;

public class CommandToggleScoreboard extends PlayerCommand {
	public CommandToggleScoreboard() {super("togglescoreboard", "<on/off>", "blobcatraz.player.toggle.scoreboard", "scoreboard", "score", "board");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("on")) {
			ScoreboardUtil.update(p);
			String msg = prefix + "Your scoreboard is now enabled!";
			p.sendMessage(msg);
		} else if(sub.equals("off")) {
			ScoreboardUtil.disable(p);
			String msg = prefix + "Your scoreboard is now disabled!";
			p.sendMessage(msg);
		} else {
			String error = getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
}