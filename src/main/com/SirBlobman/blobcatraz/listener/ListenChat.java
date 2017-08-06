package com.SirBlobman.blobcatraz.listener;

import static com.SirBlobman.blobcatraz.command.staff.CommandChatControl.mute;

import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Player.Spigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.chat.TextComponent;

public class ListenChat implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void chat(AsyncPlayerChatEvent e) {
		String f = Util.color("&f%1s &7>>&f %2s");
		e.setFormat(f);
		
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if(p.hasPermission("blobcatraz.chat.color")) {
			String c = Util.color(msg);
			e.setMessage(c);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void ping(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String pname = p.getName();
		String msg = e.getMessage();
		
		for(Player o : Bukkit.getOnlinePlayers()) {
			String oname = o.getName();
			if(!oname.equals(pname)) {
				String l1 = msg.toLowerCase();
				String l2 = oname.toLowerCase();
				if(l1.contains(l2)) PlayerUtil.ping(o);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.LOWEST)
	public void mute(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(mute) {
			String perm = "blobcatraz.chat.bypass";
			if(p.hasPermission(perm)) e.setCancelled(false);
			else {
				e.setCancelled(true);
				String msg = Util.PREFIX + "The chat is currently muted!";
				p.sendMessage(msg);
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void staff(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String msg = e.getMessage();
		if(msg.startsWith("@")) {
			String perm = "blobcatraz.staff.chat";
			if(p.hasPermission(perm)) {
				e.setCancelled(true);
				String pname = p.getName();
				String msg1 = msg.substring(1);
				for(Player o : Bukkit.getOnlinePlayers()) {
					if(o.hasPermission(perm)) {
						String msg2 = Util.format("&c[Staff] &a%1s &8>&f %2s", pname, msg1);
						o.sendMessage(msg2);
					}
				}
			} else e.setCancelled(false);
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void item(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		if(msg.contains("[item]")) {
			Player p = e.getPlayer();
			ItemStack is = PlayerUtil.held(p);
			e.setCancelled(true);
			if(!ItemUtil.air(is)) {
				TextComponent txt = text(p, msg, is);
				for(Player o : e.getRecipients()) {
					Spigot s = o.spigot();
					s.sendMessage(txt);
				}
			} else {
				String error = "You are not holding an item!";
				p.sendMessage(error);
			}
		}
	}
	
	private TextComponent text(Player p, String o, ItemStack is) {
		String disp = p.getDisplayName();
		String[] msg = o.split("\\[item\\]");
		String c = Util.color(disp + " &7>>&f ");
		TextComponent txt = new TextComponent(c);
		if(msg.length > 1) {
			for(String s : msg) {
				TextComponent tc = new TextComponent(s);
				TextComponent ho = ItemUtil.getHover(is);
				tc.addExtra(ho);
				txt.addExtra(tc);
			}
		} else {
			TextComponent hov = ItemUtil.getHover(is);
			txt.addExtra(hov);
		}
		return txt;
	}
}