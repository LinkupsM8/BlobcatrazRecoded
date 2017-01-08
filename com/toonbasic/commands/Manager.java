package com.toonbasic.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Manager implements CommandExecutor {
	private final String cmdName;
	private final String perms;
	private boolean canConsoleUse;
	public static JavaPlugin plugin;
	
	public final static void registerCommands(JavaPlugin pl) {
		plugin = pl;
	}
	
	public Manager(String cmdName, String perms, boolean canConsoleUse) {
		this.cmdName = cmdName;
		this.perms = perms;
		this.canConsoleUse = canConsoleUse;
		plugin.getCommand(cmdName).setExecutor(this);
	}
}
