package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class CommandBlock extends PlayerCommand {
	public CommandBlock() {super("block", "", "blobcatraz.player.block", "condense", "compact", "compress");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		if(!craft(pi, Material.COAL, Material.COAL_BLOCK, 9) &
		!craft(pi, Material.DIAMOND, Material.DIAMOND_BLOCK, 9) &
		!craft(pi, Material.EMERALD, Material.EMERALD_BLOCK, 9) &
		!craft(pi, Material.GOLD_INGOT, Material.GOLD_BLOCK, 9) &
		!craft(pi, Material.IRON_INGOT, Material.IRON_BLOCK, 9) &
		!craft(pi, Material.INK_SACK, (short) 4, Material.LAPIS_BLOCK, 9) &
		!craft(pi, Material.QUARTZ, Material.QUARTZ_BLOCK, 4) &
		!craft(pi, Material.REDSTONE, Material.REDSTONE_BLOCK, 9)) {
			String msg = prefix + "Not enough materials!";
			p.sendMessage(msg);
		} else {
			String msg = prefix + "Successfully combined your materials!";
			p.sendMessage(msg);
		}
	}
	
	public static boolean craft(Inventory i, Material m, Material b, int combine) {
		int materials = 0;
		for (ItemStack is : i.getContents()) {
			if (is == null) continue;
			Material mat = is.getType();
			if (mat == m) {
				materials += is.getAmount();
				i.remove(is);
			}
		}
		i.addItem(new ItemStack(b, (int) Math.floor(materials / combine)));
		i.addItem(new ItemStack(m, materials - ((int) Math.floor(materials / combine) * combine)));
		if (materials < 9) return false;
		return true;
	}
	
	public static boolean craft(Inventory i, Material m, short d, Material b, int combine) {
		int materials = 0;
		for (ItemStack is : i.getContents()) {
			if (is == null) continue;
			Material mat = is.getType();
			if (mat == m) {
				materials += is.getAmount();
				i.remove(is);
			}
		}
		i.addItem(new ItemStack(b, (int) Math.floor(materials / combine)));
		i.addItem(new ItemStack(m, materials - ((int) Math.floor(materials / combine) * combine), d));
		if (materials < 9) return false;
		return true;
	}
}