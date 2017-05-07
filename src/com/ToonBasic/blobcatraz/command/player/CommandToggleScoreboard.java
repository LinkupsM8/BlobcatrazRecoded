package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ScoreboardUtil;

@PlayerOnly
public class CommandToggleScoreboard extends ICommand {
	public CommandToggleScoreboard() {super("scoreboard", "<on/off>", "blobcatraz.player.noscoreboard", "score", "board");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String sub = args[0].toLowerCase();
		if(sub.equals("off")) {
			ScoreboardUtil.remove(p);
			p.sendMessage("Your scoreboard is now disabled!");
		} else if(sub.equals("on")) {
			ScoreboardUtil.update(p);
			p.sendMessage("Your scoreboard is now enabled!");
		} else {
			String error = prefix + Language.INCORRECT_USAGE;
			p.sendMessage(error);
		}
	}
}