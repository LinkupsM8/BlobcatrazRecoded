package com.SirBlobman.blobcatraz.economy.command.player;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.NumberUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandWorth extends ICommand {
	public CommandWorth() {super("worth", "", "blobcatraz.player.worth", "value");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is != null && is.getType() != Material.AIR) {
			Material mat = is.getType();
			String name = mat.name();
			short data = is.getDurability();
			String item = name + ":" + data;
			double worth = ItemUtil.worth(is);
			String msg = prefix + Util.color("&eYour &a" + item + "&e is worth " + NumberUtil.money(worth));
			p.sendMessage(msg);
		} else {
			String error = prefix + "AIR is not valueable!";
			p.sendMessage(error);
		}
	}
}