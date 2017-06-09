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
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSpy extends ICommand implements Listener {
	
	public CommandSpy() {super("spy", "", "blobcatraz.staff.spy");}
	
	@Override
	public void handleCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		ConfigDatabase.toggleSpy(p);
		p.sendMessage("\u00a7aYou have toggled spy " + (ConfigDatabase.spy(p) ? "on" : "off") + ".");
		
	}
	
	@EventHandler
	public void cmd(PlayerCommandPreprocessEvent e) {	
		Player send = e.getPlayer();
		String cmd = e.getMessage();
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(!p.equals(send) && ConfigDatabase.spy(p)) {
				String msg = Util.color("&2<Spy: &a" + send.getName() + "&2> " + cmd);
				p.sendMessage(msg);
			}
		}
		
	}
}
