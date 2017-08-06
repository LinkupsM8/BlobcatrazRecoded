package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigCustomItems;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import net.md_5.bungee.api.chat.TextComponent;

public class CommandItem extends PlayerCommand implements TabCompleter {
	public CommandItem() {super("item", "<item> [amount] [meta] [nbt]", "blobcatraz.staff.item", "selfgive", "i");}
	
	@Override
	public void run(Player p, String[] args) {
		String id = args[0];
		int amount = 1;
		short data = 0;
		if(args.length > 1) amount = NumberUtil.getInteger(args[1]);
		if(args.length > 2) data = NumberUtil.toShort(NumberUtil.getInteger(args[2]));
		if(amount < 1) {
			String error = "Invalid amount value! Defaulting to 1";
			p.sendMessage(error);
			amount = 1;
		} if(data < 0) {
			String error = "Invalid data value! Defaulting to 0";
			p.sendMessage(error);
			data = 0;
		}
		
		ItemStack give = ItemUtil.getItem(id, amount, data);
		if(args.length > 3) {
			String nbt = Util.finalArgs(3, args);
			try {
				ItemStack copy = ItemUtil.setNBT(give, nbt);
				give(p, copy);
				return;
			} catch(Throwable ex) {
				String error = "Invalid NBT Tag: '" + nbt + "':\n" + ex.getMessage();
				p.sendMessage(error);
				return;
			}
		} 
		
		give(p, give);
	}
	
	@Override
	public List<String> onTabComplete(CommandSender cs, Command c, String label, String[] args) {
		if(cs instanceof Player) {
			int l = args.length;
			if(l == 1) {
				String arg = args[0];
				List<String> list = ids();
				List<String> match = Util.matching(list, arg);
				return match;
			} else if(l == 2) {
				String arg = args[1];
				List<String> list = range(1, 64);
				List<String> match = Util.matching(list, arg);
				return match;
			} else if(l == 3) {
				List<String> list = range(1, 15);
				String arg = args[2];
				List<String> match = Util.matching(list, arg);
				return match;
			} else if(l == 4) {
				List<String> list = Util.newList("{}");
				return list;
			} else return null;
		} else return null;
	}
	
	private List<String> ids() {
		List<String> list = Util.newList(ConfigCustomItems.items().keySet());
		Material[] mm = Material.values();
		for(Material mat : mm) {
			String name = mat.name();
			String l = name.toLowerCase();
			list.add(l);
		}
		return list;
	}
	
	private List<String> range(int min, int max) {
		List<String> list = Util.newList();
		if(min > max) return list;
		for(int i = min; i <= max; i++) {
			String s = Integer.toString(i);
			list.add(s);
		}
		return list;
	}
	
	private void give(Player p, ItemStack is) {
		if(ItemUtil.air(is)) {
			String error = "Invalid Item ID";
			p.sendMessage(error);
		} else {
			PlayerInventory pi = p.getInventory();
			Map<Integer, ItemStack> map = pi.addItem(is);
			String msg = Util.color(prefix + "&fYou received &e");
			
			TextComponent txt = new TextComponent(msg);
			TextComponent item = ItemUtil.getGiveHover(is);
			txt.addExtra(item);
			
			Spigot s = p.spigot();
			s.sendMessage(txt);
			
			if(!map.isEmpty()) {
				String error = "Your inventory is too full! Dumping the rest of the items on the ground.";
				p.sendMessage(error);
				World w = p.getWorld();
				Location l = p.getLocation();
				for(ItemStack drop : map.values()) w.dropItem(l, drop);
			}
		}
	}
}