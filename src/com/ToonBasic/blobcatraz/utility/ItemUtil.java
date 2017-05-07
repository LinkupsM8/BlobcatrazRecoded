package com.ToonBasic.blobcatraz.utility;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.config.ConfigCustomItems;
import com.ToonBasic.blobcatraz.config.ConfigWorth;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_11_R1.NBTTagCompound;

public class ItemUtil extends Util {
	private static Map<String, Enchantment> enchants = newMap();	
	public static Map<String, ItemStack> special() {return ConfigCustomItems.items();}
	public static Map<String, Enchantment> customEnchants() {return enchants;}
	public static void load() {
		enchants.put("sharpness", Enchantment.DAMAGE_ALL);
		enchants.put("bane of spiders", Enchantment.DAMAGE_ARTHROPODS);
		enchants.put("smite", Enchantment.DAMAGE_UNDEAD);
		enchants.put("fire aspect", Enchantment.FIRE_ASPECT);
		enchants.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
		enchants.put("blast protection", Enchantment.PROTECTION_EXPLOSIONS);
		enchants.put("fire protection", Enchantment.PROTECTION_FIRE);
		enchants.put("feather falling", Enchantment.PROTECTION_FALL);
		enchants.put("flame", Enchantment.ARROW_FIRE);
		enchants.put("power", Enchantment.ARROW_DAMAGE);
		enchants.put("punch", Enchantment.ARROW_KNOCKBACK);
		enchants.put("infinity", Enchantment.ARROW_INFINITE);
		enchants.put("looting", Enchantment.LOOT_BONUS_MOBS);
		enchants.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
		enchants.put("efficiency", Enchantment.DIG_SPEED);
	}
	
	public static final ItemStack opSword() {
		ItemStack is = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta meta = is.getItemMeta();	
		meta.setDisplayName(Util.color("&4&ki&1Overpowered&4&ki&r"));
		meta.addEnchant(Enchantment.DAMAGE_ALL, 32767, true);
		meta.addEnchant(Enchantment.FIRE_ASPECT, 32767, true);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.values());
		is.setItemMeta(meta);		
		return is;
	}
	
	public static final ItemStack opBow() {
		ItemStack is = new ItemStack(Material.BOW);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Util.color("&4&ki&1Overpowered&4&ki&r"));
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 32767, true);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 32767, true);
		meta.addEnchant(Enchantment.ARROW_FIRE, 32767, true);
		meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 10, true);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.values());
		is.setItemMeta(meta);
		return is;
	}
	
	public static final ItemStack opTool(Material mat) {
		ItemStack is = new ItemStack(mat);
		ItemMeta meta = is.getItemMeta();
		meta.setDisplayName(Util.color("&4&ki&1Overpowered&4&ki&r"));
		meta.addEnchant(Enchantment.DIG_SPEED, 32767, true);
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.values());
		is.setItemMeta(meta);
		return is;
	}
	
	public static ItemStack dye(int meta) {
		ItemStack dye = new ItemStack(Material.INK_SACK, 1, toShort(meta));
		return dye;
	}
	
	public static ItemStack getItem(String id, int amount, short data) {
		Map<String, ItemStack> special = special();
		Material mat = Material.matchMaterial(id.toUpperCase());
		id = id.toLowerCase().replace(' ', '_');
		if(mat != null) {
			ItemStack is = new ItemStack(mat, amount, data);
			return is;
		} else if(special.containsKey(id)){
			ItemStack is = special.get(id);
			if(is == null) return new ItemStack(Material.AIR);
			is.setAmount(amount);
			if(is.getDurability() == 0) is.setDurability(data);
			return is;
		} else {
			ItemStack air = new ItemStack(Material.AIR);
			return air;
		}
	}
	
	public static boolean air(ItemStack is) {
		if(is == null) return true;
		Material mat = is.getType();
		boolean air = (mat == Material.AIR);
		return air;
	}
	
	public static net.minecraft.server.v1_11_R1.ItemStack toNMS(ItemStack is) {
		net.minecraft.server.v1_11_R1.ItemStack nms = CraftItemStack.asNMSCopy(is);
		return nms;
	}
	
	public static String name(ItemStack is) {
		if(air(is)) return "AIR";
		ItemMeta meta = is.getItemMeta();
		if(meta.hasDisplayName()) return meta.getDisplayName();
		else {
			String name = toNMS(is).getName();
			if(name == null) {
				Material mat = is.getType();
				String type = mat.name();
				short data = is.getDurability();
				boolean strange = ((data == 0) || (data == 32767));
				name = strange ? type : (type + ":" + data);
			}
			return name;
		}
	}
	
	public static String nbt(ItemStack is) {
		NBTTagCompound nbt = new NBTTagCompound();
		toNMS(is).save(nbt);
		String s = nbt.toString();
		return s;
	}
	
	public static TextComponent getHover(ItemStack is) {
		net.minecraft.server.v1_11_R1.ItemStack nms = toNMS(is);
		String name1 = nms.getName();
		int count = nms.getCount();
		boolean x = (count > 1);
		String name2 = x ? (name1 + " &fx" + count) : name1;
		String name3 = color("&b<&r" + name2 + "&b>&r");
		TextComponent txt = new TextComponent(name3);
		ComponentBuilder cb = new ComponentBuilder(nbt(is));
		BaseComponent[] bc = cb.create();
		HoverEvent he = new HoverEvent(Action.SHOW_ITEM, bc);
		txt.setHoverEvent(he);
		return txt;
	}
	
	public static Enchantment getEnchant(String name) {
		String name2 = name.toLowerCase();
		if(enchants.containsKey(name2)) return enchants.get(name2);
		else {
			name2 = name2.toUpperCase();
			Enchantment ench = Enchantment.getByName(name2);
			return ench;
		}
	}
	
	public static double worth(ItemStack is) {
		double worth = ConfigWorth.worth(is);
		return worth;
	}
	
	public static double worth(ItemStack... items) {
		double worth = 0.0D;
		for(ItemStack is : items) {worth = worth + worth(is);}
		return worth;
	}
	
	public static double worth(Inventory i) {
		double worth = 0.0D;
		ItemStack[] items = i.getContents();
		for(ItemStack is : items) {worth = worth + worth(is);}
		return worth;
	}
}