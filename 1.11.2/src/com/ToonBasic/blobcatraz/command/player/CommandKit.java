package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigKits;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandKit extends ICommand {
	public CommandKit() {super("kit", "[name]", "blobcatraz.player.kit");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length == 0) list(p);
		else {
			String name = Util.finalArgs(0, args);
			if(ConfigKits.exists(name)) {
				String kperm = "blobcatraz.kits." + name;
				if(p.hasPermission(kperm)) {
					ConfigKits.give(p, name);
					p.sendMessage(prefix + Util.color("You have received kit &c" + name));
				} else {
					String error = "Do you really need that kit? Too bad!";
					p.sendMessage(error);
				}
			} else {
				String error = "That is not a valid kit!";
				p.sendMessage(error);
			}
		}
	}
	
	private void list(Player p) {
		String perm = "blobcatraz.player.kits";
		if(p.hasPermission(perm)) {
			StringBuffer buff = new StringBuffer();
			List<String> list = ConfigKits.kits();
			for(int i = 0; i < list.size(); i++) {
				String name = list.get(i);
				String perm2 = perm + "." + name;
				if(p.hasPermission(perm2)) {
					if(i != 0) buff.append(Util.color("&r, "));
					buff.append(Util.color("&2" + name));
				}
			}
			p.sendMessage(prefix + "List of kits:");
			p.sendMessage(buff.toString());
		} else {
			String error = "You are not allowed to list kits!";
			p.sendMessage(error);
		}
	}
}