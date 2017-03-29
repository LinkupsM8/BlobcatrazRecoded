package com.ToonBasic.blobcatraz.command.special;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigPortals;
import com.ToonBasic.blobcatraz.listener.ListenPortal;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandPortal extends ICommand {
	public CommandPortal() {super("portal", "[wand]", "blobcatraz.special.portal");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if (args.length == 0) {
			List<String> list = ConfigPortals.getPortalList();
			StringBuilder str = new StringBuilder();
			for(String s : list) {
				str.append(s + " ");
			}
			p.sendMessage(prefix + "Portal list:");
			p.sendMessage("\u00a72" + str.toString());
		} else if (args[0].equals("wand")) {
			if (!p.hasPermission("blobcatraz.special.portal.wand")) {
				p.sendMessage(Util.prefix + "Permission \u00a7cblobcatraz.special.portal.wand \u00a7rrequired!");
				return;
			}
			p.getInventory().addItem(ListenPortal.wand());
			p.sendMessage(prefix + "\u00a72Portal Wand \u00a7radded to inventory!");
		}
	}
}