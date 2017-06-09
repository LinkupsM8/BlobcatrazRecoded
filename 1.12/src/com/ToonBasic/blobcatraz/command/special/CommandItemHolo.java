package com.ToonBasic.blobcatraz.command.special;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigHolo;
import com.ToonBasic.blobcatraz.config.CustomHologram;
import com.ToonBasic.blobcatraz.utility.NumberUtil;

@PlayerOnly
public class CommandItemHolo extends ICommand {
	public CommandItemHolo() {super("itemholo", "", "blobcatraz.special.hologram", "holoitem");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String sub = args[0].toLowerCase();
			if(sub.equals("reload")) {
				ConfigHolo.load();
				String msg = prefix + "Reloaded all item holograms!";
				p.sendMessage(msg);
			} else if(sub.equals("delete") && args.length > 1) {
				int r = NumberUtil.getInteger(args[1]);
				List<CustomHologram> near = ConfigHolo.near(p, r);
				ConfigHolo.remove(near.toArray(new CustomHologram[0]));
				String msg = prefix + "Deleted nearby holograms in a radius of " + r;
				p.sendMessage(msg);
			} else {
				String error = prefix + Language.INCORRECT_USAGE;
				p.sendMessage(error);
			}
		} else {
			PlayerInventory pi = p.getInventory();
			ItemStack is = pi.getItemInMainHand();
			if(is != null && (is.getType() != Material.AIR)) {
				Location l = p.getLocation();
				World w = l.getWorld();
				double x = l.getBlockX() + 0.5D;
				double y = l.getBlockY() + 1.0D;
				double z = l.getBlockZ() + 0.5D;
				Location n = new Location(w, x, y, z);
				ConfigHolo.create(is, n);
				String msg = "Created Hologram with the item in your hand!";
				p.sendMessage(msg);
			} else {
				String error = prefix + "You cannot create a hologram of AIR!";
				p.sendMessage(error);
			}
		}
	}
}