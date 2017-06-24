package com.SirBlobman.blobcatraz.command.player;

import static com.SirBlobman.blobcatraz.command.staff.CommandSetHub.HUB;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

public class CommandHub extends PlayerCommand {
	public CommandHub() {super("hub", "", "blobcatraz.player.hub", "lobby", "spawn");}
	
	@Override
	public void run(Player p, String[] args) {
		Location l = HUB;
		p.teleport(l);
		String msg = prefix + "Welcome to the lobby!";
		p.sendMessage(msg);
	}
}