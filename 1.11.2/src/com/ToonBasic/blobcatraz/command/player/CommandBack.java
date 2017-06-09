package com.ToonBasic.blobcatraz.command.player;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandBack extends ICommand implements Listener {
	public static Map<Player, Location> backs = Util.newMap();
	public CommandBack() {super("back", "", "blobcatraz.player.back", "return");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(backs.containsKey(p)) {
			Location back = backs.get(p);
			p.teleport(back);
			String msg = "Welcome back!";
			p.sendMessage(msg);
		} else {
			String error = "You don't have a place to go back to";
			p.sendMessage(error);
		}
	}
	
	@EventHandler
	public void tp(PlayerTeleportEvent e) {
		Player p = e.getPlayer();
		Location from = e.getFrom();
		backs.put(p, from);
	}
	
	@EventHandler
	public void die(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Location die = p.getLocation();
		backs.put(p, die);
	}
}