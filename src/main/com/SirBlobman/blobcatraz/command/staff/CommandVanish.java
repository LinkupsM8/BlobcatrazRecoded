package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandVanish extends PlayerCommand implements Listener {
	public static List<Player> vanished = Util.newList();
	private static final PotionEffectType INVIS = PotionEffectType.INVISIBILITY;
	private static final String SEE_PERM = "blobcatraz.staff.vanish.see";
	public CommandVanish() {super("vanish", "<on/off>", "blobcatraz.staff.vanish", "v");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = args[0].toLowerCase();
		if(sub.equals("on")) {
			vanished.add(p);
			for(Player o : Bukkit.getOnlinePlayers()) {
				if(o.hasPermission(SEE_PERM)) continue;
				else o.hidePlayer(p);
			}
			
			PotionEffect pe = new PotionEffect(INVIS, Integer.MAX_VALUE, 255, true);
			p.addPotionEffect(pe);
			String msg = prefix + "You are now invisible to everyone on the server";
			p.sendMessage(msg);
		} else if(sub.equals("off")) {
			if(vanished.contains(p)) vanished.remove(p);
			for(Player o : Bukkit.getOnlinePlayers()) o.showPlayer(p);
			p.removePotionEffect(INVIS);
			String msg = prefix + "You are no longer vanished!";
			p.sendMessage(msg);
		} else {
			String error = getFormattedUsage(getCommandUsed());
			p.sendMessage(error);
		}
	}
	
	@EventHandler
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPermission(SEE_PERM)) {
			for(Player o : vanished) {p.hidePlayer(o);}
		}
	}
}