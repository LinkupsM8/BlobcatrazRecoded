package com.SirBlobman.blobcatraz.shop.compat.citizens;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.listener.sign.ListenShopSign;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import net.citizensnpcs.api.util.DataKey;

@TraitName("shop")
public class ShopTrait extends Trait implements Listener {
	private static final ItemStack AIR = ItemUtil.AIR;
	public static final String TITLE = Util.color("&2&ki&3Blobcatraz Shop&2&ki&f");
	public enum ShopType {
		/**
		 * Redstone repeaters, torches, pistons,<br/>
		 * droppers, dispensers, and similar blocks
		 */
		REDSTONE,
		/**
		 * Food items such as cake, steak, and golden apples
		 */
		FOOD,
		/**
		 * Farming stuff such as wheat, potatoes, hoes, carrots, and radishes
		 */
		FARMING,
		/**
		 * SkyBlock items such as water, lava, and grass
		 */
		SKYBLOCK,
		/**
		 * Tools such as axes, swords, bows, shovels, and pickaxes
		 */
		TOOLS,
		/** Default Type, will have an empty shop**/
		UNKNOWN
	}
	
	@Persist("shop type") 
	private ShopType shopType = ShopType.UNKNOWN;
	public ShopTrait() {super("shop");}
	
	public ShopType shopType() {return shopType;}
	public void setShopType(ShopType type) {shopType = type;}
	
	@Override
	public void load(DataKey key) {
		String type = key.getString("shop type");
		shopType = ShopType.valueOf(type);
		if(shopType == null) shopType = ShopType.UNKNOWN; 
	}
	
	@Override
	public void save(DataKey key) {
		key.setString("shop type", shopType.name());
	}
	
	@Override
	public void onAttach() {
		String msg = npc.getName() + " has been converted into a shop with type " + shopType;
		Util.print(msg);
	}
	
	@EventHandler
	public void click(PlayerInteractEntityEvent e) {
		Entity ent = e.getRightClicked();
		boolean isNPC = ent.hasMetadata("NPC");
		if(isNPC) {
			Player p = e.getPlayer();
			NPCRegistry reg = CitizensAPI.getNPCRegistry();
			NPC npc = reg.getNPC(ent);
			click(p, npc);
		}
	}
	
	private void click(Player p, NPC npc) {
		if(npc.hasTrait(ShopTrait.class)) {
			ShopTrait trait = npc.getTrait(ShopTrait.class);
			ShopType shop = trait.shopType();
			switch(shop) {
			case FOOD: {
				foodGUI(p);
				break;
			}
			case REDSTONE: {
				redstoneGUI(p);
				break;
			}
			case SKYBLOCK: {
				skyBlockGUI(p);
				break;
			}
			case FARMING: {
				farmingGUI(p);
				break;
			}
			case TOOLS: {
				toolsGUI(p);
				break;
			}
			default: {
				Inventory i = Bukkit.createInventory(null, 9, TITLE);
				p.openInventory(i);
				break;
			}
			}
		}
	}
	
	@EventHandler
	public void click(InventoryClickEvent e) {
		HumanEntity he = e.getWhoClicked();
		if(he instanceof Player) {
			Player p = (Player) he;
			Inventory i = e.getClickedInventory();
			if(i != null) {
				String name = i.getName();
				if(name != null) {
					if(name.equals(TITLE)) {
						e.setCancelled(true);
						ItemStack is = e.getCurrentItem();
						if(is != null) {
							Inventory shop = ListenShopSign.shop(is);
							p.openInventory(shop);
						}
					}
				}
			}
		}
	}
	
	private ItemStack newItem(Material mat) {
		ItemStack is = new ItemStack(mat);
		return is;
	}
	
	private ItemStack newItem(Material mat, int amount, int meta) {
		ItemStack is = newItem(mat);
		if(ItemUtil.air(is)) return is;
		is.setAmount(amount);
		short dam = NumberUtil.toShort(meta);
		is.setDurability(dam);
		return is;
	}
	
	/*private ItemStack newItem(Material mat, int amount, int dam, String disp, String... lore) {
		ItemStack is = newItem(mat, amount, dam);
		if(ItemUtil.air(is)) return is;
		else {
			ItemMeta meta = is.getItemMeta();
			meta.setDisplayName(Util.color(disp));
			lore = Util.color(lore);
			List<String> list = Util.newList(lore);
			meta.setLore(list);
			is.setItemMeta(meta);
			return is;
		}
	}*/
	
	private void foodGUI(Player p) {
		Inventory i = Bukkit.createInventory(null, 18, TITLE);
		ItemStack apple = newItem(Material.APPLE);
		ItemStack msoup = newItem(Material.MUSHROOM_SOUP);
		ItemStack bread = newItem(Material.BREAD);
		ItemStack pork = newItem(Material.GRILLED_PORK);
		ItemStack golden_apple = newItem(Material.GOLDEN_APPLE);
		ItemStack notch_apple = newItem(Material.GOLDEN_APPLE, 1, 1);
		ItemStack fish = newItem(Material.COOKED_FISH);
		ItemStack salmon = newItem(Material.COOKED_FISH, 1, 1);
		ItemStack cake = newItem(Material.CAKE);
		ItemStack cookie = newItem(Material.COOKIE);
		ItemStack steak = newItem(Material.COOKED_BEEF);
		ItemStack chicken = newItem(Material.COOKED_CHICKEN);
		ItemStack pie = newItem(Material.PUMPKIN_PIE);
		ItemStack rabbit = newItem(Material.COOKED_RABBIT);
		ItemStack rstew = newItem(Material.RABBIT_STEW);
		ItemStack mutton = newItem(Material.COOKED_MUTTON);
		ItemStack bsoup = newItem(Material.BEETROOT_SOUP);
		ItemStack[] contents = Util.newArray(
			apple, msoup, bread, pork, golden_apple, notch_apple, fish, salmon, cake,
			cookie, steak, chicken, pie, rabbit, rstew, mutton, bsoup
		);
		i.setContents(contents);
		p.openInventory(i);
	}
	
	private void redstoneGUI(Player p) {
		Inventory i = Bukkit.createInventory(null, 18, TITLE);
		ItemStack redstone = newItem(Material.REDSTONE);
		ItemStack block = newItem(Material.REDSTONE_BLOCK);
		ItemStack torch = newItem(Material.REDSTONE_TORCH_ON);
		ItemStack lever = newItem(Material.LEVER);
		ItemStack lamp = newItem(Material.REDSTONE_LAMP_OFF);
		ItemStack sensor = newItem(Material.DAYLIGHT_DETECTOR);
		ItemStack observer = newItem(Material.OBSERVER);
		ItemStack dispenser = newItem(Material.DISPENSER);
		ItemStack dropper = newItem(Material.DROPPER);
		ItemStack hopper = newItem(Material.HOPPER);
		ItemStack sbutton = newItem(Material.STONE_BUTTON);
		ItemStack wbutton = newItem(Material.WOOD_BUTTON);
		ItemStack piston = newItem(Material.PISTON_BASE);
		ItemStack sticky = newItem(Material.PISTON_STICKY_BASE);
		ItemStack trip = newItem(Material.TRIPWIRE_HOOK);
		ItemStack repeat = newItem(Material.DIODE);
		ItemStack compare = newItem(Material.REDSTONE_COMPARATOR);
		ItemStack tnt = newItem(Material.TNT);
		ItemStack[] contents = Util.newArray(
			redstone, block, torch, lever, lamp, sensor, observer, dispenser, dropper,
			hopper, sbutton, wbutton, piston, sticky, trip, repeat, compare, tnt
		);
		i.setContents(contents);
		p.openInventory(i);
	}
	
	private void skyBlockGUI(Player p) {
		Inventory i = Bukkit.createInventory(null, 18, TITLE);
		ItemStack oaksap = newItem(Material.SAPLING, 1, 0);
		ItemStack sprsap = newItem(Material.SAPLING, 1, 1);
		ItemStack birsap = newItem(Material.SAPLING, 1, 2);
		ItemStack junsap = newItem(Material.SAPLING, 1, 3);
		ItemStack acasap = newItem(Material.SAPLING, 1, 4);
		ItemStack doasap = newItem(Material.SAPLING, 1, 5);
		ItemStack grass = newItem(Material.GRASS);
		ItemStack dirt = newItem(Material.DIRT);
		ItemStack mycel = newItem(Material.MYCEL);
		ItemStack water = newItem(Material.WATER_BUCKET);
		ItemStack lava = newItem(Material.LAVA_BUCKET);
		ItemStack bucket = newItem(Material.BUCKET);
		ItemStack[] contents = Util.newArray(
			oaksap, sprsap, birsap, junsap, acasap, doasap, AIR, AIR, AIR,
			grass, dirt, mycel, water, lava, bucket
		);
		i.setContents(contents);
		p.openInventory(i);
	}
	
	private void farmingGUI(Player p) {
		Inventory i = Bukkit.createInventory(null, 27, TITLE);
		ItemStack dhoe = newItem(Material.DIAMOND_HOE);
		ItemStack ihoe = newItem(Material.IRON_HOE);
		ItemStack ghoe = newItem(Material.GOLD_HOE);
		ItemStack shoe = newItem(Material.STONE_HOE);
		ItemStack whoe = newItem(Material.WOOD_HOE);
		
		ItemStack wheat = newItem(Material.WHEAT);
		ItemStack potato = newItem(Material.POTATO_ITEM);
		ItemStack carrot = newItem(Material.CARROT_ITEM);
		ItemStack beetroot = newItem(Material.BEETROOT);
		ItemStack melon = newItem(Material.MELON);
		ItemStack bmelon = newItem(Material.MELON_BLOCK);
		ItemStack pumpkin = newItem(Material.PUMPKIN);
		
		ItemStack wseeds = newItem(Material.SEEDS);
		ItemStack bseeds = newItem(Material.BEETROOT_SEEDS);
		ItemStack mseeds = newItem(Material.MELON_SEEDS);
		ItemStack pseeds = newItem(Material.PUMPKIN_SEEDS);
		
		ItemStack[] contents = Util.newArray(
			dhoe, ihoe, ghoe, shoe, whoe, AIR, AIR, AIR, AIR,
			wheat, potato, carrot, beetroot, melon, bmelon, pumpkin, AIR, AIR,
			wseeds, bseeds, mseeds, pseeds
		);
		i.setContents(contents);
		p.openInventory(i);
	}
	
	private void toolsGUI(Player p) {
		Inventory i = Bukkit.createInventory(null, 45, TITLE);
		ItemStack dsword = newItem(Material.DIAMOND_SWORD);
		ItemStack isword = newItem(Material.IRON_SWORD);
		ItemStack gsword = newItem(Material.GOLD_SWORD);
		ItemStack ssword = newItem(Material.STONE_SWORD);
		ItemStack wsword = newItem(Material.WOOD_SWORD);
		
		ItemStack daxe = newItem(Material.DIAMOND_AXE);
		ItemStack iaxe = newItem(Material.IRON_AXE);
		ItemStack gaxe = newItem(Material.GOLD_AXE);
		ItemStack saxe = newItem(Material.STONE_AXE);
		ItemStack waxe = newItem(Material.WOOD_AXE);
		
		ItemStack dpick = newItem(Material.DIAMOND_PICKAXE);
		ItemStack ipick = newItem(Material.IRON_PICKAXE);
		ItemStack gpick = newItem(Material.GOLD_PICKAXE);
		ItemStack spick = newItem(Material.STONE_PICKAXE);
		ItemStack wpick = newItem(Material.WOOD_PICKAXE);
		
		ItemStack dspad = newItem(Material.DIAMOND_SPADE);
		ItemStack ispad = newItem(Material.IRON_SPADE);
		ItemStack gspad = newItem(Material.GOLD_SPADE);
		ItemStack sspad = newItem(Material.STONE_SPADE);
		ItemStack wspad = newItem(Material.WOOD_SPADE);
		
		ItemStack bow = newItem(Material.BOW);
		ItemStack arrow = newItem(Material.ARROW);
		ItemStack arrow2 = newItem(Material.SPECTRAL_ARROW);
		
		ItemStack[] contents = Util.newArray(
			dsword, AIR, isword, AIR, gsword, AIR, ssword, AIR, wsword,
			daxe, AIR, iaxe, AIR, gaxe, AIR, saxe, AIR, waxe,
			dpick, AIR, ipick, AIR, gpick, AIR, spick, AIR, wpick,
			dspad, AIR, ispad, AIR, gspad, AIR, sspad, AIR, wspad,
			bow, AIR, arrow, AIR, arrow2
		);
		i.setContents(contents);
		p.openInventory(i);
	}
}