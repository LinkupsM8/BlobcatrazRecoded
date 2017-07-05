package com.SirBlobman.blobcatraz.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.SirBlobman.blobcatraz.utility.Util;

public abstract class ICommand implements CommandExecutor {
	private String cmd;
	private String use;
	private Permission perm;
	private String[] aliases;
	private int minArgs = 0;
	private boolean player = false;
	private boolean disabled = false;
	private CommandSender cs;
	private String used;
	
	public String prefix = Util.PREFIX;
	public ICommand(String name) {this(name, "");}
	public ICommand(String name, String usage) {this(name, usage, "blobcatraz.staff");}
	public ICommand(String name, String usage, String perm, String... aliases) {
		this.cmd = name;
		this.use = usage;
		this.aliases = aliases;
		this.perm = new Permission(perm);
		
		boolean n1 = (usage != null);
		boolean n2 = (!usage.equals(""));
		if(n1 && n2) {
			Pattern p = Pattern.compile("<.*?>");
			Matcher m = p.matcher(usage);
			while(m.find()) {this.minArgs += 1;}
		}
		
		Class<? extends ICommand> clazz = getClass();
		if(this instanceof PlayerCommand) this.player = true;
		if(clazz.isAnnotationPresent(PlayerOnly.class)) this.player = true;
		if(clazz.isAnnotationPresent(Disabled.class)) this.disabled = true;
	}
	
	@Override
	public boolean onCommand(CommandSender cs, Command c, String label, String[] args) {return execute(cs, c, label, args);}
	private boolean execute(CommandSender cs, Command c, String label, String[] args) {
		this.cs = cs;
		this.used = label;
		if(disabled) {
			String error = prefix + Language.COMMAND_DISABLED;
			cs.sendMessage(error);
			return true;
		} else {
			if(player) {
				if(!(cs instanceof Player)) {
					String error = prefix + Language.PLAYER_ONLY;
					cs.sendMessage(error);
					return true;
				}
			}
			
			boolean has = cs.hasPermission(perm);
			if(perm == null || perm.getName() == "") has = true;
			if(!has) {
				String error = prefix + Util.format(Language.NO_PERMISSION, perm.getName());
				cs.sendMessage(error);
				return true;
			}
			
			if(args.length < minArgs) {
				String error = prefix + getFormattedUsage(label);
				cs.sendMessage(error);
				return true;
			} else {
				try {run(cs, args);} 
				catch(Throwable ex) {
					String error = Util.color("&cThere was an error while doing that command! Please report this!");
					cs.sendMessage(error);
					ex.printStackTrace();
				}
			}
			return true;
		}
	}
	
	public String getFormattedUsage(String cmd) {
		String us = (cmd + " " + use);
		String f = Util.format(Language.INCORRECT_USAGE, us);
		return f;
	}
	
	public abstract void run(CommandSender cs, String[] args);
	public CommandExecutor getExecutor() {return this;}
	public String getCommand() {return cmd;}
	public String getUsage() {return use;}
	public CommandSender getSender() {return cs;}
	public String[] getAliases() {return aliases;}
	public String getCommandUsed() {return used;}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface PlayerOnly {}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Disabled {}
	
	public enum Language {
		NO_PERMISSION("&cYou don't have permission: &b%1s"),
		PLAYER_ONLY("&cYou are not a Player"),
		INVALID_TARGET("&cInvalid Target: '%1s&c'"),
		INCORRECT_USAGE("&cIncorrect Usage, please try again with these arguments: \n&f/%1s"),
		COMMAND_DISABLED("&cThis command is disabled!")
		;
		
		private String msg;
		Language(String msg) {this.msg = Util.color(msg);}
		public String toString() {return msg;}
	}
}