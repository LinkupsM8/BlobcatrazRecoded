package com.ToonBasic.blobcatraz.utility;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.ToonBasic.blobcatraz.config.ConfigWorth;
import com.ToonBasic.blobcatraz.listener.item.ListenSonic;

public class ItemUtil extends Util {
	/*Start Spawn Eggs*/
	
	/*End Spawn Eggs*/
	
	/*Start Dyes*/
	private static final ItemStack ROSE = dye(1);
	private static final ItemStack CACTUS = dye(2);
	private static final ItemStack COCOA = dye(3);
	private static final ItemStack LAPIS = dye(4);
	private static final ItemStack PURPLE = dye(5);
	private static final ItemStack CYAN = dye(6);
	private static final ItemStack LIGHT_GRAY = dye(7);
	private static final ItemStack GRAY = dye(8);
	private static final ItemStack PINK = dye(9);
	private static final ItemStack LIME = dye(10);
	private static final ItemStack DANDELION = dye(11);
	private static final ItemStack LIGHT_BLUE = dye(12);
	private static final ItemStack MAGENTA = dye(13);
	private static final ItemStack ORANGE = dye(14);
	private static final ItemStack BONE_MEAL = dye(15);
	/*End Dyes*/
	
	/*Start Special Items*/
	private static final ItemStack ENCHANTED_GOLDEN_APPLE = new ItemStack(Material.GOLDEN_APPLE, 1, toShort(1));
	private static final ItemStack ENDER_PORTAL_FRAME = new ItemStack(Material.ENDER_PORTAL_FRAME, 1, toShort(0));
	private static final ItemStack OVERPOWERED_AXE = opTool(Material.DIAMOND_AXE);
	private static final ItemStack OVERPOWERED_PICKAXE = opTool(Material.DIAMOND_PICKAXE);
	private static final ItemStack OVERPOWERED_SHOVEL = opTool(Material.DIAMOND_SPADE);
	/*End Special Items*/
	
	/*Start Stones*/
	private static final ItemStack GRANITE = new ItemStack(Material.STONE, 1, toShort(1));
	private static final ItemStack PGRANITE = new ItemStack(Material.STONE, 1, toShort(2));
	private static final ItemStack DIORITE = new ItemStack(Material.STONE, 1, toShort(3));
	private static final ItemStack PDIORITE = new ItemStack(Material.STONE, 1, toShort(4));
	private static final ItemStack ANDESITE = new ItemStack(Material.STONE, 1, toShort(5));
	private static final ItemStack PANDESITE = new ItemStack(Material.STONE, 1, toShort(6));
	/*End Stones*/
	
	private static Map<String, ItemStack> items = newMap();
	private static Map<String, Enchantment> enchants = newMap();
	
	public static Map<String, ItemStack> special() {return items;}
	public static Map<String, Enchantment> customEnchants() {return enchants;}
	public static void load() {
		items.put("op sword", opSword());
		items.put("op bow", opBow());
		items.put("op axe", OVERPOWERED_AXE);
		items.put("op pickaxe", OVERPOWERED_PICKAXE);
		items.put("op shovel", OVERPOWERED_SHOVEL);
		items.put("sonic", ListenSonic.sonic());
		items.put("rose red", ROSE);
		items.put("cactus green", CACTUS);
		items.put("cocoa beans", COCOA);
		items.put("lapis lazuli", LAPIS);
		items.put("purple dye", PURPLE);
		items.put("cyan dye", CYAN);
		items.put("light gray dye", LIGHT_GRAY);
		items.put("gray dye", GRAY);
		items.put("pink dye", PINK);
		items.put("lime dye", LIME);
		items.put("dandelion yellow", DANDELION);
		items.put("light blue dye", LIGHT_BLUE);
		items.put("magenta dye", MAGENTA);
		items.put("orange dye", ORANGE);
		items.put("bone meal", BONE_MEAL);
		items.put("notch apple", ENCHANTED_GOLDEN_APPLE);
		items.put("granite", GRANITE);
		items.put("diorite", DIORITE);
		items.put("andesite", ANDESITE);
		items.put("polished granite", PGRANITE);
		items.put("polished diorite", PDIORITE);
		items.put("polished andesite", PANDESITE);
		items.put("portal frame", ENDER_PORTAL_FRAME);
		items.put("enchant table", new ItemStack(Material.ENCHANTMENT_TABLE));
		items.put("sticky piston", new ItemStack(Material.PISTON_STICKY_BASE));
		
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
		if(mat != null) {
			ItemStack is = new ItemStack(mat, amount, data);
			return is;
		} else if(special.containsKey(id)){
			ItemStack is = special.get(id);
			if(is == null) return new ItemStack(Material.AIR);
			is.setAmount(amount);
			if(is.getDurability() == 0) is.setDurability(data);
			return is;
		}
		return null;
	}
	
	public static String name(ItemStack is) {
		if(is == null) return "AIR";
		ItemMeta meta = is.getItemMeta();
		if(meta.hasDisplayName()) return meta.getDisplayName();
		else {
			Material mat = is.getType();
			short data = is.getDurability();
			if(data == 0 || data == 32767) return mat.name();
			else {
				String name = mat.name() + ":" + data;
				return name;
			}
		}
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