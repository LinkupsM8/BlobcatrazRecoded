package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;

public class CommandTPAll extends PlayerCommand {
	public CommandTPAll() {super("tpall", "", "blobcatraz.staff.tpall", "bringserver", "teleportall");}
	
	@Override
	public void run(Player p, String[] args) {
		World pw = p.getWorld();
		String msg = prefix + "Teleporting the entire server to yourself...";
		p.sendMessage(msg);
		for(Player o : Bukkit.getOnlinePlayers()) {
			boolean e = o.equals(p);
			World ow = o.getWorld();
			if(ow.equals(pw) && !e) {
				o.teleport(p);
				String msg1 = prefix + "The gods decided to summon you!";
				o.sendMessage(msg1);
			}
		}
	}
}