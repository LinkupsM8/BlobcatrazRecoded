package com.SirBlobman.blobcatraz.economy.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.md_5.bungee.api.chat.TextComponent;

public class CommandWorth extends PlayerCommand {
	public CommandWorth() {super("worth", "", "blobcatraz.player.worth", "value");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		if(is != null && is.getType() != Material.AIR) {
			double worth = ItemUtil.worth(is);
			String money = NumberUtil.money(worth);
			String msg1 = Util.color(prefix + "&eYour &a");
			String msg2 = Util.color("&e is worth " + money);
			
			TextComponent t1 = new TextComponent(msg1);
			TextComponent item = ItemUtil.getHover(is);
			TextComponent t2 = new TextComponent(msg2);
			t1.addExtra(item);
			t1.addExtra(t2);
			Spigot s = p.spigot();
			s.sendMessage(t1);
		} else {
			String error = prefix + "AIR is not valuable!";
			p.sendMessage(error);
		}
	}
}