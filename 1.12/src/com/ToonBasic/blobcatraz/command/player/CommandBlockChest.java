package com.ToonBasic.blobcatraz.command.player;

import static com.ToonBasic.blobcatraz.command.player.CommandBlock.craft;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;

@PlayerOnly
public class CommandBlockChest extends ICommand {
	public CommandBlockChest() {super("blockchest", "", "blobcatraz.player.block", "condensechest", "compactchest", "combinechest", "compresschest");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Block b = PlayerUtil.lookBlock(p);
		BlockState bs = b.getState();
		if(bs instanceof Chest) {
			Chest c = (Chest) bs;
			Inventory i = c.getInventory();
			if (!craft(i, Material.COAL, Material.COAL_BLOCK, 9) &
				!craft(i, Material.DIAMOND, Material.DIAMOND_BLOCK, 9) &
				!craft(i, Material.EMERALD, Material.EMERALD_BLOCK, 9) &
				!craft(i, Material.GOLD_INGOT, Material.GOLD_BLOCK, 9) &
				!craft(i, Material.IRON_INGOT, Material.IRON_BLOCK, 9) &
				!craft(i, Material.INK_SACK, (short) 4, Material.LAPIS_BLOCK, 9) &
				!craft(i, Material.QUARTZ, Material.QUARTZ_BLOCK, 4) &
				!craft(i, Material.REDSTONE, Material.REDSTONE_BLOCK, 9)) {
					String msg = "That chest doesn't have enough materials!";
					PlayerUtil.action(p, msg);
				} else {
					String msg = "Combined the materials inside the chest";
					PlayerUtil.action(p, msg);
				}
		} else {
			String error = "You are not looking at a chest!";
			PlayerUtil.action(p, error);
		}
	}
}