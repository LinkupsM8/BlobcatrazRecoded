package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigKits;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;
import java.util.Map;

public class CommandKit extends PlayerCommand {
	private static Map<Player, Long> COOLDOWN = Util.newMap();
	public CommandKit() {super("kit", "[name]", "blobcatraz.player.kit");}

	@Override
	public void run(Player p, String[] args) {
		if(args.length == 0) list(p);
		else {
			String name = Util.finalArgs(0, args);
			if(ConfigKits.exists(name)) {
				String perm = Util.format("blobcatraz.kits.%1s", name);
				if(p.hasPermission(perm)) {
					if(cooldown(p)) {
						PlayerInventory pi = p.getInventory();
						List<ItemStack> list = ConfigKits.kit(name);
						boolean full = false;
						List<ItemStack> drop = Util.newList();
						for(ItemStack is : list) {
							Map<Integer, ItemStack> map = pi.addItem(is);
							if(map.isEmpty()) continue;
							else {
								full = true;
								drop.addAll(map.values());
							}
						}

						if(full) {
							String error = prefix + "Your inventory is full, dumping items on the floor...";
							p.sendMessage(error);
							Location l = p.getLocation();
							World w = l.getWorld();
							for(ItemStack is : drop) w.dropItem(l, is);
						}

						String msg = Util.format(prefix + "You received a kit called '%1s'", name);
						addCooldown(p);
						p.sendMessage(msg);
					}
				} else {
					String error = Util.format(prefix + Language.NO_PERMISSION, perm);
					p.sendMessage(error);
				}
			} else {
				String error = Util.format(prefix + "Invalid Kit '%1s'.", name);
				p.sendMessage(error);
			}
		}
	}

	private void list(Player p) {
		String perm = "blobcatraz.player.kits";
		if(p.hasPermission(perm)) {
			String msg = Util.color(prefix + "List of Kits:\n");
			StringBuilder sb = new StringBuilder(msg);
			List<String> list = ConfigKits.kits();
			for(int i = 0; i < list.size(); i++) {
				String name = list.get(i);
				String perm2 = perm + "." + name;
				if(p.hasPermission(perm2)) {
					String s1 = Util.color("&r, ");
					String s2 = Util.format("&2%1s", name);

					if(i != 0) sb.append(s1);
					sb.append(s2);
				}
			}
			String s = sb.toString();
			p.sendMessage(s);
		} else {
			String error = Util.format(prefix + Language.NO_PERMISSION, perm);
			p.sendMessage(error);
		}
	}

	private boolean cooldown(Player p) {
		String perm = "blobcatraz.cooldown.bypass";
		if(!p.hasPermission(perm) && COOLDOWN.containsKey(p)) {
			long l = COOLDOWN.get(p);
			long c = System.currentTimeMillis();
			long time = l - c;
			if(time <= 0) {
				COOLDOWN.remove(p);
				return true;
			} else {
				String left = NumberUtil.timeLeft(time);
				String f = Util.color("&cYou must wait &6%1s &cbefore getting another kit!");
				String error = String.format(f, left);
				p.sendMessage(error);
				return false;
			}
		} else return true;
	}

	public static void addCooldown(Player p) {
		long c = System.currentTimeMillis();
		long l = c + (30 * 60 * 1000L);
		COOLDOWN.put(p, l);
	}
}