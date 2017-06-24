package com.SirBlobman.blobcatraz.command.special;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class CommandFakeVote extends ICommand {
	public CommandFakeVote() {super("fakevote", "<user> [service]", "blobcatraz.special.fakevote", "fv");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		try {
			String target = args[0];
			String serv = "Blobcaraz";
			if(args.length > 1) {serv = Util.finalArgs(1, args);}
			
			Vote v = new Vote(serv, target, "null", "null");
			VotifierEvent ve = new VotifierEvent(v);
			Bukkit.getPluginManager().callEvent(ve);
			String msg = Util.format(prefix + "Sent fake vote for the username &a%1s&f with the service &3%2s&r", target, serv);
			cs.sendMessage(msg);
		} catch(Exception ex) {
			String error = prefix + "Votifier is not installed.";
			cs.sendMessage(error);
		}
	}
}