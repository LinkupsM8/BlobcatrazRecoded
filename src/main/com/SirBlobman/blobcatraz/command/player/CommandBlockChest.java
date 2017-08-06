package com.SirBlobman.blobcatraz.command.player;

import static com.SirBlobman.blobcatraz.command.player.CommandBlock.craft;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;

public class CommandBlockChest extends PlayerCommand {
	public CommandBlockChest() {super("blockchest", "", "blobcatraz.player.block", "condensechest", "compresschest", "compactchest");}
	
	@Override
	public void run(Player p, String[] args) {
		Block b = PlayerUtil.lookBlock(p);
		BlockState bs = b.getState();
		if(bs instanceof Chest) {
			Chest c = (Chest) bs;
			Inventory i = c.getInventory();
			if(!craft(i, Material.COAL, Material.COAL_BLOCK, 9) &
			!craft(i, Material.DIAMOND, Material.DIAMOND_BLOCK, 9) &
			!craft(i, Material.EMERALD, Material.EMERALD_BLOCK, 9) &
			!craft(i, Material.GOLD_INGOT, Material.GOLD_BLOCK, 9) &
			!craft(i, Material.IRON_INGOT, Material.IRON_BLOCK, 9) &
			!craft(i, Material.INK_SACK, (short) 4, Material.LAPIS_BLOCK, 9) &
			!craft(i, Material.QUARTZ, Material.QUARTZ_BLOCK, 4) &
			!craft(i, Material.REDSTONE, Material.REDSTONE_BLOCK, 9)) {
				String msg = prefix + "Not enough materials!";
				p.sendMessage(msg);
			} else {
				String msg = prefix + "Successfully combined your materials!";
				p.sendMessage(msg);
			}
		} else {
			String error = "You are not looking at a chest!";
			PlayerUtil.action(p, error);
		}
	}
}