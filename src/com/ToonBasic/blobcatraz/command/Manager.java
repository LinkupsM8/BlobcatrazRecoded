package com.ToonBasic.blobcatraz.command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Manager implements CommandExecutor 
{
	private final String perms;
	private boolean canConsoleUse;
	public static JavaPlugin plugin;
	
	public final static void registerCommands(JavaPlugin pl) {plugin = pl;}
	
	public Manager(String cmdName, String perms, boolean canConsoleUse) 
	{
		this.perms = perms;
		this.canConsoleUse = canConsoleUse;
		plugin.getCommand(cmdName).setExecutor(this);
	}
	
	public void execute(final CommandSender cs, final String[] args)
	{
		if((cs instanceof ConsoleCommandSender) && !canConsoleUse) return;
		if(!cs.hasPermission(perms)) return;
	}
}
