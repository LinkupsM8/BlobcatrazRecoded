package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class CommandFakeVote extends ICommand {
	public CommandFakeVote() {super("fakevote", "<user> [service]", "blobcatraz.staff.fakevote");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		try {
			String target = args[0];
			String service = "Blobcatraz";
			if(args.length > 1) service = args[1];
			Vote v = new Vote(service, target, "null", "null");
			VotifierEvent ve = new VotifierEvent(v);
			Bukkit.getPluginManager().callEvent(ve);
			cs.sendMessage(prefix + "Sent fake vote for the username " + target);
		} catch(Throwable ex) {
			String error = "Votifier is not installed!";
			cs.sendMessage(error);
		}
	}
}