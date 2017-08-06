package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.listener.ListenPortal;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandPortal extends PlayerCommand {
	public CommandPortal() {super("portal", "[wand]", "blobcatraz.special.portal", "portals");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length == 0) {
			
		} else {
			String sub = args[0].toLowerCase();
			if(sub.equals("wand")) {
				String perm = "blobcatraz.special.portal.wand";
				if(p.hasPermission(perm)) {
					PlayerInventory pi = p.getInventory();
					ItemStack is = ListenPortal.WAND;
					pi.addItem(is);
					String msg = Util.color(prefix + "You received a &2Portal Wand");
					p.sendMessage(msg);
				} else {
					String error = Util.format(prefix + Language.NO_PERMISSION, perm);
					p.sendMessage(error);
				}
			} else {
				String error = prefix + getFormattedUsage(getCommandUsed());
				p.sendMessage(error);
			}
		}
	}
}