package com.ToonBasic.blobcatraz.command.staff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandRepair extends ICommand {
	
	List<Material> whitelist = new ArrayList<Material>(Arrays.asList(
			Material.IRON_SPADE,
			Material.IRON_PICKAXE,
			Material.IRON_AXE,
			Material.FLINT_AND_STEEL,
			Material.BOW,
			Material.IRON_SWORD,
			Material.WOOD_SWORD,
			Material.WOOD_SPADE,
			Material.WOOD_PICKAXE,
			Material.WOOD_AXE,
			Material.STONE_SWORD,
			Material.STONE_SPADE,
			Material.STONE_PICKAXE,
			Material.STONE_AXE,
			Material.DIAMOND_SWORD,
			Material.DIAMOND_SPADE,
			Material.DIAMOND_PICKAXE,
			Material.DIAMOND_AXE,
			Material.GOLD_SWORD,
			Material.GOLD_SPADE,
			Material.GOLD_PICKAXE,
			Material.GOLD_AXE,
			Material.WOOD_HOE,
			Material.STONE_HOE,
			Material.IRON_HOE,
			Material.DIAMOND_HOE,
			Material.GOLD_HOE,
			Material.LEATHER_HELMET,
			Material.LEATHER_CHESTPLATE,
			Material.LEATHER_LEGGINGS,
			Material.LEATHER_BOOTS,
			Material.CHAINMAIL_HELMET,
			Material.CHAINMAIL_CHESTPLATE,
			Material.CHAINMAIL_LEGGINGS,
			Material.CHAINMAIL_BOOTS,
			Material.IRON_HELMET,
			Material.IRON_CHESTPLATE,
			Material.IRON_LEGGINGS,
			Material.IRON_BOOTS,
			Material.DIAMOND_HELMET,
			Material.DIAMOND_CHESTPLATE,
			Material.DIAMOND_LEGGINGS,
			Material.DIAMOND_BOOTS,
			Material.GOLD_HELMET,
			Material.GOLD_CHESTPLATE,
			Material.GOLD_LEGGINGS,
			Material.GOLD_BOOTS,
			Material.FISHING_ROD,
			Material.SHEARS,
			Material.CARROT_STICK,
			Material.SHIELD,
			Material.ELYTRA
		));

	public CommandRepair() {
		super("repair", "[hand/offhand/armor/hotbar/all]", "blobcatraz.staff.repair");
	}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		
		Player p = (Player) cs;
		
		if (args.length == 0 || args[0].equals("hand")) {
			
			ItemStack i = p.getInventory().getItemInMainHand();
			Material m = i.getType();
			
			if (whitelist.contains(m)) {
				
				i.setDurability((short) 0);
				p.getInventory().setItemInMainHand(i);
				
			} else if (m.equals(Material.AIR)) p.sendMessage("§cYou can't repair nothing!");
			else p.sendMessage("§cYou can't repair this item!");
			
		} else if (args[0].equals("offhand")) {
			
			ItemStack i = p.getInventory().getItemInOffHand();
			Material m = i.getType();
			
			if (whitelist.contains(m)) {
				
				i.setDurability((short) 0);
				p.getInventory().setItemInOffHand(i);
				
			} else if (m.equals(Material.AIR)) p.sendMessage("§cYou can't repair nothing!");
			else p.sendMessage("§cYou can't repair this item!");
			
		} else if (args[0].equals("armor")) {
			
			for (ItemStack i : p.getInventory().getArmorContents()) {
				
				if (i == null) continue;
				
				Material m = i.getType();

				if (whitelist.contains(m)) {

					i.setDurability((short) 0);

				} else continue;
					
			
			}
			
		} else if (args[0].equals("hotbar")) {
			
			List<ItemStack> iList = new ArrayList<ItemStack>();
			for (int i = 0; i < 9; i++) {
				iList.add(p.getInventory().getItem(i));
			}
			
			for (ItemStack itemStack : iList) {
				
				if (itemStack == null) continue;
				
				Material m = itemStack.getType();

				if (whitelist.contains(m)) {

					itemStack.setDurability((short) 0);

				} else continue;
					
			
			}
			
		} else if (args[0].equals("all")) {
			
			for (ItemStack i : p.getInventory()) {
				
				if (i == null) continue;
				
				Material m = i.getType();

				if (whitelist.contains(m)) {

					i.setDurability((short) 0);

				} else continue;
					
			
			}
		}
		
	}

}
