package com.SirBlobman.blobcatraz.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public abstract class PlayerCommand extends ICommand {
	public PlayerCommand(String name) {super(name, "");}
	public PlayerCommand(String name, String usage) {super(name, usage, "blobcatraz.player");}
	public PlayerCommand(String name, String usage, String perm, String... aliases) {super(name, usage, perm, aliases);}

	@Override
	public void run(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		run(p, args);
	}
	
	public abstract void run(Player p, String[] args);
}
