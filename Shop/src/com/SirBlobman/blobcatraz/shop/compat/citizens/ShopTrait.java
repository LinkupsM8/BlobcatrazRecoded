package com.SirBlobman.blobcatraz.shop.compat.citizens;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

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
		/** Resources, like diamonds, sticks, iron and coal */
		RESOURCES,
		/** Armor, such as helmets, chestplates, leggings, boots, and horse armor */
		ARMOR,
		/** Spawn eggs */
		MOBS,
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
		String path = "shop type";
		String value = shopType.name();
		key.setString(path, value);
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
			if(shop == ShopType.ARMOR) armorGUI(p);
			else if(shop == ShopType.FARMING) farmingGUI(p);
			else if(shop == ShopType.FOOD) foodGUI(p);
			else if(shop == ShopType.MOBS) mobsGUI(p);
			else if(shop == ShopType.REDSTONE) redstoneGUI(p);
			else if(shop == ShopType.RESOURCES) resourceGUI(p);
			else if(shop == ShopType.SKYBLOCK) skyBlockGUI(p);
			else if(shop == ShopType.TOOLS) toolsGUI(p);
			else {
				String error = "Invalid shop! Contact an admin!";
				p.sendMessage(error);
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
	
	private ItemStack spawnEgg(EntityType et) {
		ItemStack is = newItem(Material.MONSTER_EGG);
		ItemMeta meta = is.getItemMeta();
		SpawnEggMeta egg = (SpawnEggMeta) meta;
		egg.setSpawnedType(et);
		is.setItemMeta(egg);
		return is;
	}
	
	private Inventory newInv(int i) {
		InventoryHolder ih = null;
		float div = (((float) i) / 9.0F);
		long round = Math.round(div);
		int size = (int) (9 * round);
		Inventory inv = Bukkit.createInventory(ih, size, TITLE);
		return inv;
	}
	
	private Inventory newInv(ItemStack[] contents) {
		int size = contents.length;
		Inventory i = newInv(size);
		i.setContents(contents);
		return i;
	}
	
	private void foodGUI(Player p) {
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
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
	
	private void redstoneGUI(Player p) {
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
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
	
	private void skyBlockGUI(Player p) {
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
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
	
	private void farmingGUI(Player p) {
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
		ItemStack chorus = newItem(Material.CHORUS_FLOWER);
		
		ItemStack wseeds = newItem(Material.SEEDS);
		ItemStack bseeds = newItem(Material.BEETROOT_SEEDS);
		ItemStack mseeds = newItem(Material.MELON_SEEDS);
		ItemStack pseeds = newItem(Material.PUMPKIN_SEEDS);
		ItemStack cseeds = newItem(Material.INK_SACK, 1, 3);
		
		ItemStack[] contents = Util.newArray(
			dhoe, ihoe, ghoe, shoe, whoe, AIR, AIR, AIR, AIR,
			wheat, potato, carrot, beetroot, melon, bmelon, pumpkin, chorus, AIR,
			wseeds, bseeds, mseeds, pseeds, cseeds
		);
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
	
	private void toolsGUI(Player p) {
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
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
	
	private void resourceGUI(Player p) {
		ItemStack dblock = newItem(Material.DIAMOND_BLOCK);
		ItemStack eblock = newItem(Material.EMERALD_BLOCK);
		ItemStack lblock = newItem(Material.LAPIS_BLOCK);
		ItemStack qblock = newItem(Material.QUARTZ_BLOCK);
		ItemStack rblock = newItem(Material.REDSTONE_BLOCK);
		ItemStack gblock = newItem(Material.GOLD_BLOCK);
		ItemStack iblock = newItem(Material.IRON_BLOCK);
		ItemStack cblock = newItem(Material.COAL_BLOCK);
		
		ItemStack diamond = newItem(Material.DIAMOND);
		ItemStack emerald = newItem(Material.EMERALD);
		ItemStack lapis = newItem(Material.INK_SACK, 1, 4);
		ItemStack quartz = newItem(Material.QUARTZ);
		ItemStack redstone = newItem(Material.REDSTONE);
		ItemStack gold = newItem(Material.GOLD_INGOT);
		ItemStack iron = newItem(Material.IRON_INGOT);
		ItemStack coal = newItem(Material.COAL);
		
		ItemStack stick = newItem(Material.STICK);
		ItemStack bonem = newItem(Material.INK_SACK, 1, 15);
		ItemStack feather = newItem(Material.FEATHER);
		ItemStack glowstone = newItem(Material.GLOWSTONE_DUST);
		ItemStack sulfur = newItem(Material.SULPHUR);
		ItemStack endstone = newItem(Material.ENDER_STONE);
		ItemStack[] contents = Util.newArray(
			dblock, eblock, lblock, qblock, rblock, gblock, iblock, cblock, AIR,
			diamond, emerald, lapis, quartz, redstone, gold, iron, coal, AIR,
			stick, bonem, feather, glowstone, sulfur, endstone
		);
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
	
	private void armorGUI(Player p) {
		ItemStack dhelm = newItem(Material.DIAMOND_HELMET);
		ItemStack dches = newItem(Material.DIAMOND_CHESTPLATE);
		ItemStack dlegs = newItem(Material.DIAMOND_LEGGINGS);
		ItemStack dboot = newItem(Material.DIAMOND_BOOTS);

		ItemStack ihelm = newItem(Material.IRON_HELMET);
		ItemStack iches = newItem(Material.IRON_CHESTPLATE);
		ItemStack ilegs = newItem(Material.IRON_LEGGINGS);
		ItemStack iboot = newItem(Material.IRON_BOOTS);

		ItemStack chelm = newItem(Material.CHAINMAIL_HELMET);
		ItemStack cches = newItem(Material.CHAINMAIL_CHESTPLATE);
		ItemStack clegs = newItem(Material.CHAINMAIL_LEGGINGS);
		ItemStack cboot = newItem(Material.CHAINMAIL_BOOTS);

		ItemStack ghelm = newItem(Material.GOLD_HELMET);
		ItemStack gches = newItem(Material.GOLD_CHESTPLATE);
		ItemStack glegs = newItem(Material.GOLD_LEGGINGS);
		ItemStack gboot = newItem(Material.GOLD_BOOTS);

		ItemStack lhelm = newItem(Material.LEATHER_HELMET);
		ItemStack lches = newItem(Material.LEATHER_CHESTPLATE);
		ItemStack llegs = newItem(Material.LEATHER_LEGGINGS);
		ItemStack lboot = newItem(Material.LEATHER_BOOTS);
		
		ItemStack saddle = newItem(Material.SADDLE);
		ItemStack ihorse = newItem(Material.IRON_BARDING);
		ItemStack ghorse = newItem(Material.GOLD_BARDING);
		ItemStack dhorse = newItem(Material.DIAMOND_BARDING);
		
		ItemStack[] contents = Util.newArray(
			dhelm, dches, dlegs, dboot, AIR, ihelm, iches, ilegs, iboot,
			chelm, cches, clegs, cboot, AIR, ghelm, gches, glegs, gboot,
			lhelm, lches, llegs, lboot, AIR, saddle, ihorse, ghorse, dhorse
		);
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
	
	private void mobsGUI(Player p) {
		ItemStack spawner = newItem(Material.MOB_SPAWNER);
		ItemStack bat = spawnEgg(EntityType.BAT);
		ItemStack blaze = spawnEgg(EntityType.BLAZE);
		ItemStack cavespider = spawnEgg(EntityType.CAVE_SPIDER);
		ItemStack chicken = spawnEgg(EntityType.CHICKEN);
		ItemStack cow = spawnEgg(EntityType.COW);
		ItemStack creeper = spawnEgg(EntityType.CREEPER);
		ItemStack donkey = spawnEgg(EntityType.DONKEY);
		ItemStack elderguardian = spawnEgg(EntityType.ELDER_GUARDIAN);
		ItemStack enderman = spawnEgg(EntityType.ENDERMAN);
		ItemStack endermite = spawnEgg(EntityType.ENDERMITE);
		ItemStack evoker = spawnEgg(EntityType.EVOKER);
		ItemStack ghast = spawnEgg(EntityType.GHAST);
		ItemStack guardian = spawnEgg(EntityType.GUARDIAN);
		ItemStack horse = spawnEgg(EntityType.HORSE);
		ItemStack husk = spawnEgg(EntityType.HUSK);
		ItemStack llama = spawnEgg(EntityType.LLAMA);
		ItemStack magmacube = spawnEgg(EntityType.MAGMA_CUBE);
		ItemStack mooshroom = spawnEgg(EntityType.MUSHROOM_COW);
		ItemStack mule = spawnEgg(EntityType.MULE);
		ItemStack ocelot = spawnEgg(EntityType.OCELOT);
		ItemStack pig = spawnEgg(EntityType.PIG);
		ItemStack polarbear = spawnEgg(EntityType.POLAR_BEAR);
		ItemStack rabbit = spawnEgg(EntityType.RABBIT);
		ItemStack sheep = spawnEgg(EntityType.SHEEP);
		ItemStack shulker = spawnEgg(EntityType.SHULKER);
		ItemStack silverfish = spawnEgg(EntityType.SILVERFISH);
		ItemStack skeleton = spawnEgg(EntityType.SKELETON);
		ItemStack skeletonhorse = spawnEgg(EntityType.SKELETON_HORSE);
		ItemStack slime = spawnEgg(EntityType.SLIME);
		ItemStack spider = spawnEgg(EntityType.SPIDER);
		ItemStack squid = spawnEgg(EntityType.SQUID);
		ItemStack stray = spawnEgg(EntityType.STRAY);
		ItemStack vex = spawnEgg(EntityType.VEX);
		ItemStack villager = spawnEgg(EntityType.VILLAGER);
		ItemStack vindicator = spawnEgg(EntityType.VINDICATOR);
		ItemStack witch = spawnEgg(EntityType.WITCH);
		ItemStack witherskeleton = spawnEgg(EntityType.WITHER_SKELETON);
		ItemStack wolf = spawnEgg(EntityType.WOLF);
		ItemStack zombie = spawnEgg(EntityType.ZOMBIE);
		ItemStack zombiehorse = spawnEgg(EntityType.ZOMBIE_HORSE);
		ItemStack zombiepigman = spawnEgg(EntityType.PIG_ZOMBIE);
		ItemStack zombievillager = spawnEgg(EntityType.ZOMBIE_VILLAGER);
		ItemStack[] contents = Util.newArray(
			spawner, bat, blaze, cavespider, chicken, cow, creeper, donkey, elderguardian,
			enderman, endermite, evoker, ghast, guardian, horse, husk, llama, magmacube,
			mooshroom, mule, ocelot, pig, polarbear, rabbit, sheep, shulker, silverfish,
			skeleton, skeletonhorse, slime, spider, squid, stray, vex, villager, vindicator,
			witch, witherskeleton, wolf, zombie, zombiehorse, zombiepigman, zombievillager
		);
		Inventory i = newInv(contents);
		p.openInventory(i);
	}
}