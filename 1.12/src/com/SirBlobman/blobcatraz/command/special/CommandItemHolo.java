package com.SirBlobman.blobcatraz.command.special;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigHolo;
import com.SirBlobman.blobcatraz.config.special.CustomHologram;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CommandItemHolo extends PlayerCommand {
	public CommandItemHolo() {super("itemholo", "[reload/delete] [range]", "blobcatraz.special.hologram", "holoitem");}
	
	@Override
	public void run(Player p, String[] args) {
		if(args.length > 0) {
			String sub = args[0].toLowerCase();
			if(sub.equals("reload")) {
				ConfigHolo.load();
				String msg = prefix + "Reloaded all item holograms!";
				p.sendMessage(msg);
			} else if(sub.equals("delete") && args.length > 1) {
				int r = NumberUtil.getInteger(args[1]);
				List<CustomHologram> near = ConfigHolo.near(p, r);
				CustomHologram[] ch = near.toArray(new CustomHologram[0]);
				ConfigHolo.remove(ch);
				String msg = Util.format(prefix + "Delete nearby holograms in a radius of %1s", r);
				p.sendMessage(msg);
			} else {
				String error = prefix + getFormattedUsage(getCommandUsed());
				p.sendMessage(error);
			}
		} else {
			ItemStack is = PlayerUtil.held(p);
			if(ItemUtil.air(is)) {
				String error = prefix + "You cannot create a hologram of AIR!";
				p.sendMessage(error);
			} else {
				Location o = p.getLocation();
				World w = o.getWorld();
				double x = o.getBlockX() + 0.5D;
				double y = o.getBlockY() + 1.0D;
				double z = o.getBlockZ() + 0.5D;
				
				Location n = new Location(w, x, y, z);
				ConfigHolo.create(is, n);
				String msg = "Created a hologram with the item in your hand!";
				p.sendMessage(msg);
			}
		}
	}
}