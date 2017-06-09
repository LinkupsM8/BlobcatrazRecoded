package com.ToonBasic.blobcatraz.command.special;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigPortals;
import com.ToonBasic.blobcatraz.listener.ListenPortal;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandPortal extends ICommand {
	public CommandPortal() {super("portal", "[wand]", "blobcatraz.special.portal", "portals");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if (args.length == 0) {
			List<String> list = ConfigPortals.getPortalList();
			String s = Util.listToString(list, "&r, ");
			String[] msg = Util.color(prefix + "Portal list:", "&2" + s);
			p.sendMessage(msg);
		} else {
			String sub = args[0].toLowerCase();
			if(sub.equals("wand")) {
				String perm = "blobcatraz.special.portal.wand";
				if (!p.hasPermission(perm)) {
					String msg = Util.color(prefix + "You don't have permission! &f(&a" + perm + "&f)");
					p.sendMessage(msg);
					return;
				} else {
					PlayerInventory pi = p.getInventory();
					ItemStack wand = ListenPortal.wand();
					pi.addItem(wand);
					String msg = Util.color(prefix + "&2Portal Wand &radded to your inventory");
					p.sendMessage(msg);
				}
			}
		}
	}
}