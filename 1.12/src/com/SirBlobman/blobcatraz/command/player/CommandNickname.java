package com.SirBlobman.blobcatraz.command.player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class CommandNickname extends PlayerCommand implements Listener {
	public CommandNickname() {super("nickname", "<name>", "blobcatraz.player.nickname", "nick");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		if(name.equalsIgnoreCase("off")) name = p.getName();
		String nick = Util.format("%1s&r", name);
		String pre = ConfigDatabase.prefix(p);
		ConfigDatabase.setNick(p, name);
		p.setDisplayName(pre + nick);
		String msg = Util.format(prefix + "Your nickname was changed to %1s", nick);
		p.sendMessage(msg);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void nick(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String pre = ConfigDatabase.prefix(p);
		String nic = ConfigDatabase.nick(p);
		p.setDisplayName(pre + nic);
	}
}