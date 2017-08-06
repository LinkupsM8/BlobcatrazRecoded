package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;
import java.util.Set;

public class CommandSetHome extends PlayerCommand {
	public CommandSetHome() {super("sethome", "[name]", "blobcatraz.player.sethome", "createhome", "addhome");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = p.getLocation();
		if(args.length == 0) {
			String name = "home";
			ConfigDatabase.setHome(p, name, l);
			String msg = Util.format(prefix + "You set a home called '%1s' as your current location.", name);
			p.sendMessage(msg);
		} else {
			int max = 0;
			String start = "blobcatraz.player.sethome.";
			String infin = start + "infinite";
			Set<PermissionAttachmentInfo> set = p.getEffectivePermissions();
			for(PermissionAttachmentInfo pai : set) {
				String perm = pai.getPermission();
				if(perm.startsWith(start) && !perm.equals(infin)) {
					try {
						String sub = perm.substring(26);
						max = Integer.parseInt(sub);
					} catch(Throwable ex) {
						String error = Util.format("Invalid permission '%1s', please end with a number or 'infinite'.", perm);
						Util.print(error);
					}
				}
			}
			
			List<String> list = ConfigDatabase.homes(p);
			int size = list.size();
			if(p.hasPermission(infin) || size < max) {
				String name = Util.finalArgs(0, args);
				ConfigDatabase.setHome(p, name, l);
				String msg = Util.format(prefix + "You set a home called '%1s' as your current location.", name);
				p.sendMessage(msg);
			} else {
				String err = start + max + " &cor &b" + infin;
				String msg = Util.format(prefix + Language.NO_PERMISSION, err);
				p.sendMessage(msg);
			}
		}
	}
}