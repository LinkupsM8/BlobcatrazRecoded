package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;

@PlayerOnly
public class CommandSpy extends ICommand implements Listener {
	
	public CommandSpy() {
		
		super("spy", null, "blobcatraz.player.spy");
		
	}
	
	@Override
	public void handleCommand(CommandSender sender, String[] args) {
		
		Player p = (Player) sender;
		
		ConfigDatabase.toggleSpy(p);
		p.sendMessage("\u00a7aYou have toggled spy " + (ConfigDatabase.spy(p) ? "on" : "off") + ".");
		
	}
	
	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
		
		Player sender = e.getPlayer();
		for (Player p : Bukkit.getServer().getOnlinePlayers()) if (!p.equals(sender)) p.sendMessage("\u00a7a<" + sender.getName() + "> " + e.getMessage());
		
	}
}
