package com.ToonBasic.blobcatraz.command.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.ToonBasic.blobcatraz.PublicHandlers;
import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.config.ConfigDatabase;

public class CommandNickname extends ICommand implements Listener {
	public CommandNickname() {super("nickname", "<name>", "blobcatraz.player.nickname", "nick");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String name = PublicHandlers.finalArgs(0, args);
		String nick = PublicHandlers.color(name);
		Player p = (Player) cs;
		p.setDisplayName(nick);
		ConfigDatabase.nickName(p, name);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	private void nick(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		String nick = ConfigDatabase.nickName(p);
		p.setDisplayName(nick);
	}
}