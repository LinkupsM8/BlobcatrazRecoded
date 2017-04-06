package com.ToonBasic.blobcatraz.utility;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.listener.item.ListenSonic;

public class ItemUtil extends Util {
	private static Map<String, ItemStack> ITEMS = newMap();
	public static void load() {
		ITEMS.put("op sword", opSword());
		ITEMS.put("sonic", ListenSonic.sonic());
	}
	public static Map<String, ItemStack> special() {return ITEMS;} 
	
	public static final ItemStack opSword() {
		ItemStack is = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = is.getItemMeta();	
		meta.setDisplayName(Util.color("&4&ki&1Overpowered &1Sword&4&ki&r"));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 32767, true);
		meta.addEnchant(Enchantment.DURABILITY, 32767, true);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 32767, true);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE);
		is.setItemMeta(meta);		
		return is;
	}
	
	public static ItemStack getItem(String id, int amount, short data) {
		Map<String, ItemStack> special = special();
		Material mat = Material.matchMaterial(id);
		if(mat != null) {
			ItemStack is = new ItemStack(mat, amount, data);
			return is;
		} else if(special.containsKey(id)){
			ItemStack is = special.get(id);
			is.setAmount(amount);
			is.setDurability(data);
			return is;
		}
		return null;
	}
}