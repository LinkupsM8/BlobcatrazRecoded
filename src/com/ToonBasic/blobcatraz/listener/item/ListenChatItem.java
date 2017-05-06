package com.ToonBasic.blobcatraz.listener.item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

import net.md_5.bungee.api.chat.TextComponent;

public class ListenChatItem implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void item(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		if(msg.contains("[item]")) {
			Player p = e.getPlayer();
			PlayerInventory pi = p.getInventory();
			ItemStack is = pi.getItemInMainHand();
			if(ItemUtil.air(is)) {
				e.setCancelled(true);
				String error = "You cannot show off if you don't have anything!";
				p.sendMessage(error);
			} else {
				e.setCancelled(true);
				TextComponent tc = text(p, msg, is);
				for(Player o : Bukkit.getOnlinePlayers()) {
					Spigot s = o.spigot();
					s.sendMessage(tc);
				}
			}
		}
	}
	
	private TextComponent text(Player p, String omsg, ItemStack is) {
		String disp = p.getDisplayName();
		String[] msg = omsg.split("\\[item\\]");
		TextComponent txt = new TextComponent(Util.color(disp + " &7>>&f "));
		if(msg.length > 0) {
			for(String s : msg) {
				TextComponent t = new TextComponent(s);
				TextComponent hov = ItemUtil.getHover(is);
				t.addExtra(hov);
				txt.addExtra(t);
			}
		} else {
			TextComponent hov = ItemUtil.getHover(is);
			txt.addExtra(hov);
		}
		return txt;
	}
}