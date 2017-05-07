package com.SirBlobman.itemutil.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.utility.Util;

import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class ItemUtil extends Util {
	public static boolean air(ItemStack is) {
		if(is == null) return true;
		Material mat = is.getType();
		boolean air = (mat == Material.AIR);
		return air;
	}
	
	public static ItemMeta meta(ItemStack is) {
		if(air(is)) return null;
		ItemMeta meta = is.getItemMeta();
		return meta;
	}
	
	public static void rename(ItemStack is, String name) {
		if(air(is)) return;
		ItemMeta meta = meta(is);
		String disp = color(name);
		meta.setDisplayName(disp);
		is.setItemMeta(meta);		
	}
	
	public static void setLore(ItemStack is, String... ss) {
		if(air(is)) return;
		ItemMeta meta = meta(is);
		List<String> lore = newList(color(ss));
		meta.setLore(lore);
		is.setItemMeta(meta);
	}
	
	public static void addLore(ItemStack is, String... ss) {
		if(air(is)) return;
		ItemMeta meta = meta(is);
		if(meta.hasLore()) {
			List<String> lore = meta.getLore();
			for(String s : ss) lore.add(color(s));
			meta.setLore(lore);
			is.setItemMeta(meta);
		} else setLore(is, ss);
	}
	
	public static void hideAll(ItemStack is) {
		ItemMeta meta = meta(is);
		ItemFlag[] flags = ItemFlag.values();
		meta.addItemFlags(flags);
		is.setItemMeta(meta);
	}
	
	public static void setUnbreakable(ItemStack is) {
		ItemMeta meta = meta(is);
		meta.setUnbreakable(true);
		is.setItemMeta(meta);
	}
	
/*Stuff past this point does not change meta/nbt*/	
	public static net.minecraft.server.v1_11_R1.ItemStack toNMS(ItemStack is) {return CraftItemStack.asNMSCopy(is);}

	public static String name(ItemStack is) {
		if(air(is)) {return "AIR";}
		ItemMeta meta = meta(is);
		if(meta.hasDisplayName()) {
			String disp = meta.getDisplayName();
			return disp;
		} else {
			try {
				net.minecraft.server.v1_11_R1.ItemStack nms = toNMS(is);
				String name = nms.getName();
				return name;
			} catch(Throwable ex) {
				Material mat = is.getType();
				String type = mat.name();
				short data = is.getDurability();
				boolean strange = ((data == 0) || (data == 32767) || (data == -1));
				String name = strange ? type : (type + ":" + data);
				return name;
			}
		}
	}
	
	public static List<String> lore(ItemStack is) {
		if(air(is)) return newList();
		ItemMeta meta = meta(is);
		if(meta.hasLore()) {
			List<String> lore = meta.getLore();
			return lore;
		} else return newList();
	}
	
	public static String nbt(ItemStack is) {
		if(air(is)) return "{}";
		net.minecraft.server.v1_11_R1.ItemStack nms = toNMS(is);
		NBTTagCompound nbt = new NBTTagCompound();
		nms.save(nbt);
		String s = nbt.toString();
		return s;
	}
}