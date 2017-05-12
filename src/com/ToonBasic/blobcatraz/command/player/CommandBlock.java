package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandBlock extends ICommand {
	public CommandBlock() {super("block", "", "blobcatraz.player.block", "condense", "compact", "combine", "compress");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if (			
			!craft(p.getInventory(), Material.COAL, Material.COAL_BLOCK, 9) &
			!craft(p.getInventory(), Material.DIAMOND, Material.DIAMOND_BLOCK, 9) &
			!craft(p.getInventory(), Material.EMERALD, Material.EMERALD_BLOCK, 9) &
			!craft(p.getInventory(), Material.GOLD_INGOT, Material.GOLD_BLOCK, 9) &
			!craft(p.getInventory(), Material.IRON_INGOT, Material.IRON_BLOCK, 9) &
			!craft(p.getInventory(), Material.INK_SACK, (short) 4, Material.LAPIS_BLOCK, 9) &
			!craft(p.getInventory(), Material.QUARTZ, Material.QUARTZ_BLOCK, 4) &
			!craft(p.getInventory(), Material.REDSTONE, Material.REDSTONE_BLOCK, 9)			
		) {
			p.sendMessage(prefix + "Not enough materials!");
			return;
		}
		p.sendMessage(prefix + "Combined your materials!");
	}
	
	public static boolean craft(Inventory i, Material m, Material b, int combine) {
		int materials = 0;
		for (ItemStack is : i.getContents()) {
			if (is == null) continue;
			if (is.getType().equals(m)) {
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
			if (is.getType().equals(m)) {
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