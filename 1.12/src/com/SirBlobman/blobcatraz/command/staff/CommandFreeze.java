package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandFreeze extends PlayerCommand implements Listener {
	private static List<Player> frozen = Util.newList();
	public CommandFreeze() {super("freeze", "<player>", "blobcatraz.staff.freeze", "glue");}
	
	@Override
	public void run(Player p, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String name = t.getName();
			if(frozen.contains(t)) {
				frozen.remove(t);
				String msg1 = prefix + "The sun finally melted you...";
				String msg2 = Util.format(prefix + "You melted &b%1s", name);
				t.sendMessage(msg1);
				p.sendMessage(msg2);
			} else {
				frozen.add(t);
				String msg1 = prefix + "You are now frozen and cannot move!";
				String msg2 = Util.format(prefix + "You froze &b%1s", name);
				t.sendMessage(msg1);
				p.sendMessage(msg2);
			}
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			p.sendMessage(error);
		}
	}
	
	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(frozen.contains(p)) {
			Location l1 = e.getFrom();
			Location l2 = e.getTo();
			if(changed(l1, l2)) {
				e.setCancelled(true);
				String error = prefix + "You are frozen!";
				PlayerUtil.action(p, error);
			}
		}
	}
	
	private boolean changed(Location l1, Location l2) {
		World w1 = l1.getWorld();
		World w2 = l2.getWorld();
		int x1 = l1.getBlockX(), x2 = l2.getBlockX(),
		y1 = l1.getBlockY(), y2 = l2.getBlockY(),
		z1 = l1.getBlockZ(), z2 = l2.getBlockZ();
		
		boolean w = (w1.equals(w2));
		boolean x = (x1 == x2);
		boolean y = (y1 == y2);
		boolean z = (z1 == z2);
		boolean b = (w && x && y && z);
		return !b;
	}
}