package com.SirBlobman.blobcatraz.utility;

import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.SirBlobman.blobcatraz.config.ConfigCustomItems;
import com.SirBlobman.blobcatraz.config.ConfigWorth;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.MojangsonParseException;
import net.minecraft.server.v1_12_R1.MojangsonParser;
import net.minecraft.server.v1_12_R1.NBTTagCompound;


public class ItemUtil extends Util {
	public static Map<String, Enchantment> ENCHANTS = Util.newMap();
	public static Map<String, ItemStack> SPECIAL = ConfigCustomItems.items();
	public static final ItemStack AIR = newItem(Material.AIR);

	private static void rege(String k, Enchantment v) {ENCHANTS.put(k, v);}
	public static void load() {
		ConfigCustomItems.load();
		rege("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
		rege("fire_protection", Enchantment.PROTECTION_FIRE);
		rege("feather_falling", Enchantment.PROTECTION_FALL);
		rege("blast_protection", Enchantment.PROTECTION_EXPLOSIONS);
		rege("projectile_protection", Enchantment.PROTECTION_PROJECTILE);
		rege("arrow_protection", Enchantment.PROTECTION_PROJECTILE);
		rege("water_breathing", Enchantment.OXYGEN);
		rege("aqua_affinity", Enchantment.WATER_WORKER);
		rege("thorns", Enchantment.THORNS);
		rege("depth_strider", Enchantment.DEPTH_STRIDER);
		rege("frost_walker", Enchantment.FROST_WALKER);
		rege("binding", Enchantment.BINDING_CURSE);
		rege("sharpness", Enchantment.DAMAGE_ALL);
		rege("smite", Enchantment.DAMAGE_UNDEAD);
		rege("bane_of_arthropods", Enchantment.DAMAGE_ARTHROPODS);
		rege("bane_of_spiders", Enchantment.DAMAGE_ARTHROPODS);
		rege("knockback", Enchantment.KNOCKBACK);
		rege("fire_aspect", Enchantment.FIRE_ASPECT);
		rege("looting", Enchantment.LOOT_BONUS_MOBS);
		rege("sweeping_edge", Enchantment.SWEEPING_EDGE);
		rege("efficiency", Enchantment.DIG_SPEED);
		rege("silk_touch", Enchantment.SILK_TOUCH);
		rege("unbreaking", Enchantment.DURABILITY);
		rege("fortune", Enchantment.LOOT_BONUS_BLOCKS);
		rege("power", Enchantment.ARROW_DAMAGE);
		rege("punch", Enchantment.ARROW_KNOCKBACK);
		rege("infinity", Enchantment.ARROW_INFINITE);
		rege("flame", Enchantment.ARROW_FIRE);
		rege("luck", Enchantment.LUCK);
		rege("lure", Enchantment.LURE);
		rege("mending", Enchantment.MENDING);
		rege("vanishing", Enchantment.VANISHING_CURSE);
	}
	
	public static boolean air(ItemStack is) {
		if(is == null) return true;
		if(is.equals(AIR)) return true;
		Material mat = is.getType();
		boolean air = (mat == Material.AIR);
		return air;
	}
	
	public static ItemStack getItem(String id, int amount, int data) {
		Map<String, ItemStack> special = SPECIAL;
		id = id.toLowerCase().replace(' ', '_');
		String up = id.toUpperCase();
		Material mat = Material.matchMaterial(up);
		if(mat != null) {
			ItemStack is = newItem(mat, amount, data);
			return is;
		} else if(special.containsKey(id)) {
			ItemStack is = special.get(id);
			if(air(is)) return AIR;
			is.setAmount(amount);
			return is;
		} else return AIR;
	}
	
	public static ItemStack newItem(Material mat) {
		ItemStack is = new ItemStack(mat);
		return is;
	}
	
	public static ItemStack newItem(Material mat, int amount) {
		ItemStack is = newItem(mat);
		if(!air(is)) is.setAmount(amount);
		return is;
	}
	
	public static ItemStack newItem(Material mat, int amount, int data) {
		ItemStack is = newItem(mat, amount);
		if(!air(is)) {
			short meta = NumberUtil.toShort(data);
			is.setDurability(meta);
		}
		return is;
	}
	
	public static ItemStack newItem(Material mat, int amount, int data, String name) {
		ItemStack is = newItem(mat, amount, data);
		if(!air(is)) {
			ItemMeta meta = is.getItemMeta();
			String disp = color(name);
			meta.setDisplayName(disp);
			meta.addItemFlags(ItemFlag.values());
			is.setItemMeta(meta);
		}
		return is;
	}
	
	public static ItemStack newItem(Material mat, int amount, int data, String name, String... lore) {
		ItemStack is = newItem(mat, amount, data, name);
		if(!air(is)) {
			ItemMeta meta = is.getItemMeta();
			lore = Util.color(lore);
			List<String> list = newList(lore);
			meta.setLore(list);
			is.setItemMeta(meta);
		}
		return is;
	}
	
	public static ItemStack newHead(String owner, String disp, String... lore) {
		ItemStack is = newItem(Material.SKULL_ITEM, 1, 3, disp, lore);
		ItemMeta meta = is.getItemMeta();
		SkullMeta sm = (SkullMeta) meta;
		sm.setOwner(owner);
		is.setItemMeta(sm);
		return is;
	}
	
	public static net.minecraft.server.v1_12_R1.ItemStack toNMS(ItemStack is) {
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(is);
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
		return nbt.toString();
	}
	
	public static ItemStack setNBT(ItemStack is, String data) throws MojangsonParseException {
		ItemStack ni = is.clone();
		net.minecraft.server.v1_12_R1.ItemStack nms = toNMS(ni);
		NBTTagCompound nbt = MojangsonParser.parse(data);
		nms.setTag(nbt);
		ItemMeta meta = CraftItemStack.getItemMeta(nms);
		ni.setItemMeta(meta);
		return ni;
	}
	
	public static TextComponent getHover(ItemStack is) {
		net.minecraft.server.v1_12_R1.ItemStack nms = toNMS(is);
		String name1 = nms.getName();
		int count = nms.getCount();
		boolean x = (count > 1);
		String name2 = x ? (name1 + "&f x" + count) : name1;
		String name3 = color("&b<&r" + name2 + "&b>&r");
		TextComponent txt = new TextComponent(name3);
		String nbt = nbt(is);
		ComponentBuilder cb = new ComponentBuilder(nbt);
		BaseComponent[] bc = cb.create();
		HoverEvent he = new HoverEvent(Action.SHOW_ITEM, bc);
		txt.setHoverEvent(he);
		return txt;
	}
	
	public static TextComponent getGiveHover(ItemStack is) {
		net.minecraft.server.v1_12_R1.ItemStack nms = toNMS(is);
		String name1 = nms.getName();
		String name2 = color("&r" + name1 + "&r");
		TextComponent txt = new TextComponent(name2);
		String nbt = nbt(is);
		ComponentBuilder cb = new ComponentBuilder(nbt);
		BaseComponent[] bc = cb.create();
		HoverEvent he = new HoverEvent(Action.SHOW_ITEM, bc);
		txt.setHoverEvent(he);
		return txt;
	}
	
	public static Enchantment getEnchant(String name) {
		String name2 = name.toLowerCase();
		String name3 = name2.replace(' ', '_');
		if(ENCHANTS.containsKey(name3)) return ENCHANTS.get(name3);
		else {
			String name4 = name3.toUpperCase();
			Enchantment ench = Enchantment.getByName(name4);
			return ench;
		}
	}
	
	public static double worth(ItemStack is) {
		double d = ConfigWorth.worth(is);
		return d;
	}
	
	public static double worth(ItemStack... items) {
		double worth = 0.0D;
		for(ItemStack is : items) {
			double d = worth(is);
			worth += d;
		}
		return worth;
	}
	
	public static double worth(Inventory i) {
		ItemStack[] items = i.getContents();
		double worth = worth(items);
		return worth;
	}
}