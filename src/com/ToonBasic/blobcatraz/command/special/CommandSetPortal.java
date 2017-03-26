package com.ToonBasic.blobcatraz.command.special;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigPortals;
import com.ToonBasic.blobcatraz.listener.ListenPortal;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSetPortal extends ICommand {
	
	public CommandSetPortal() {
		super("setportal", "<name>", "blobcatraz.special.setportal", "createportal");
	}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		
		Player p = (Player) cs;
		
		if(args.length > 0) {
			
			String name = Util.finalArgs(0, args);
			Location dest = p.getLocation();
			if (ListenPortal.getPrimary(p.getUniqueId()) == null || ListenPortal.getSecondary(p.getUniqueId()) == null) {
				p.sendMessage(prefix + "You must set both portal wand locations before creating a portal!");
				return;
			}
			ConfigPortals.save(name, dest, ListenPortal.getPrimary(p.getUniqueId()), ListenPortal.getSecondary(p.getUniqueId()));
			p.sendMessage(prefix + "You have set portal \u00a72" + name + "  \u00a7rto your current location");
			
		}
	}
}