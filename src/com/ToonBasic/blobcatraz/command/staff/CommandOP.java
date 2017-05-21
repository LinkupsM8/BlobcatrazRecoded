package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandOP extends ICommand {
	public CommandOP() {super("op", "<player>", "blobcatraz.staff.op", "operator", "addop");}
	
	@Override
	@SuppressWarnings("deprecation")
	public void handleCommand(CommandSender cs, String[] args) {
		if(CommandOPPassword.allowedToOP(cs)) {
			String target = args[0];
			OfflinePlayer op = Bukkit.getOfflinePlayer(target);
			if(op == null) {
				String error = prefix + Language.INVALID_TARGET;
				cs.sendMessage(error);
			} else {
				op.setOp(true);
				if(op.isOnline()) {
					Player p = op.getPlayer();
					p.sendMessage("You are now OP");
				}
				CommandOPPassword.remove(cs);
				String msg = "You OPPED " + op.getName() + ". Your password has now expired";
				cs.sendMessage(msg);
			}
		} else {
			String error = Util.color("&4&lInvalid token!");
			cs.sendMessage(error);
		}
	}
}