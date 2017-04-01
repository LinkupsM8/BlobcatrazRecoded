package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSetHome extends ICommand {
	
	public CommandSetHome() {
		super("sethome", "[name]", "blobcatraz.player.sethome", "createhome");
	}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Location dest = p.getLocation();
		if (args.length == 0) {
			ConfigDatabase.setHome(p, "home", dest);
			p.sendMessage(prefix + "You have set home \u00a72home \u00a7rto your current location");
		} else if (args.length > 0) {
			int maxHomes = 0;
			for (PermissionAttachmentInfo pai : p.getEffectivePermissions()) {
				String perm = pai.getPermission();
				if (perm.startsWith("blobcatraz.player.sethome.") && !perm.equals("blobcatraz.player.sethome.infinite")) {
					try {
						maxHomes = Integer.parseInt(perm.substring(26));
					} catch (Exception e) {
						Util.print("Invalid permission, please end with a number or \"infinite\": " + perm);
					}
				}
			}
			if (p.hasPermission("blobcatraz.player.sethome.infinite") || ConfigDatabase.getHomes(p).size() < maxHomes) {
				String name = Util.finalArgs(0, args);
				ConfigDatabase.setHome(p, name, dest);
				p.sendMessage(prefix + "You have set home \u00a72" + name + " \u00a7rto your current location");
			} else {
				p.sendMessage(prefix + "Permission \u00a7cblobcatraz.player.sethome.infinite \u00a7ror \u00a7cblobcatraz.player.sethome.<integer> \u00a7rrequired!");
			}
		}
	}
}