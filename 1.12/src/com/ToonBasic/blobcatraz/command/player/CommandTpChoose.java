package com.ToonBasic.blobcatraz.command.player;

import static com.ToonBasic.blobcatraz.command.player.CommandTpa.tplist;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandTpChoose extends ICommand {
	public CommandTpChoose() {super("tpchoose", "[deny/accept]", "blobcatraz.player.tpa", "tpaccept", "tpdeny");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player t = (Player) cs;
		if(tplist.containsKey(t)) {
			Player p = tplist.get(t);
			String sub = (args.length > 0) ? args[0] : this.getCommandUsed();
			sub = sub.toLowerCase();
			if(sub.equals("accept") || sub.equals("tpaccept")) {
				t.sendMessage("Request accepted!");
				p.sendMessage(prefix + Util.color("&a" + t.getName() + " &ehas accepted your request!"));
				p.sendMessage(Util.color("&eTeleporting..."));
				p.teleport(t);
				tplist.remove(t);
			} else if(sub.equals("deny") || sub.equals("tpdeny")) {
				t.sendMessage("Request denied!");
				p.sendMessage(prefix + Util.color("&eYour request to tp to &a" + t.getName() + " &ewas denied!"));
				tplist.remove(t);
			} else {
				String error = Language.INCORRECT_USAGE;
				t.sendMessage(error);
			}
		} else {
			String error = "You do not have any requests!";
			t.sendMessage(error);
		}
	}
}