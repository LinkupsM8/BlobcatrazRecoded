package com.SirBlobman.blobcatraz.command;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import com.SirBlobman.blobcatraz.utility.Util;

public class BlobcatrazCommand extends Command implements PluginIdentifiableCommand {
	private final Plugin plugin;
	private CommandExecutor cx;
	private TabCompleter tc;
	
	public BlobcatrazCommand(String label, CommandExecutor cx, Plugin pl) {
		super(label);
		this.cx = cx;
		this.plugin = pl;
		this.usageMessage = "";
	}
	
	@Override
	public boolean execute(CommandSender cs, String label, String[] args) {
		boolean success = false;
		boolean enabled = plugin.isEnabled();
		if(enabled) {
			boolean test = testPermission(cs);
			if(test) {
				try {
					boolean exe = cx.onCommand(cs, this, label, args);
					success = exe;
				} catch(Throwable ex) {
					PluginDescriptionFile pdf = plugin.getDescription();
					String pname = pdf.getFullName();
					String error = "Unhandled Exception in command '" + label + "' in plugin " + pname;
					CommandException ce = new CommandException(error, ex);
					throw ce;
				}
				
				boolean len = (usageMessage.length() > 0);
				if(!success && len) {
					String rep = usageMessage.replace("<command>", label);
					String[] ss = rep.split("\n");
					for(String s : ss) cs.sendMessage(s);
				}
				return success;
			} else return true;
		} else return false;
	}
	
	@Override
	public List<String> tabComplete(CommandSender cs, String label, String[] args) {
		Validate.notNull(cs, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(label, "Alias cannot be null");
        
        List<String> list = null;
        try {
        	if(tc != null) {
        		List<String> comp = tc.onTabComplete(cs, this, label, args);
        		list = comp;
        	} else if(cx instanceof TabCompleter) {
        		TabCompleter tab = (TabCompleter) cx;
        		List<String> comp = tab.onTabComplete(cs, this, label, args);
        		list = comp;
        	}
        } catch(Throwable ex) {
        	PluginDescriptionFile pdf = plugin.getDescription();
        	String pname = pdf.getFullName();
        	String fin = Util.finalArgs(0, args);
        	String msg = "Unhandled exception during tab completion for command '/" + label + " " + fin + "' in plugin " + pname;
        	CommandException ce = new CommandException(msg, ex);
        	throw ce;
        }
        
        if(list == null) return super.tabComplete(cs, label, args);
        return list;
	}
	
	public void setExecutor(CommandExecutor cx) {
		CommandExecutor cc = (cx == null ? plugin : cx);
		this.cx = cc;
	}
	
	public void setTabCompleter(TabCompleter tc) {this.tc = tc;}
	public CommandExecutor getExecutor() {return cx;}
	public TabCompleter getTabCompleter() {return tc;}
	public Plugin getPlugin() {return plugin;}
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        int del = (sb.length() - 1);
        sb.deleteCharAt(del);
        sb.append(", ");
        
        PluginDescriptionFile pdf = plugin.getDescription();
        String pname = pdf.getFullName();
        sb.append(pname);
        sb.append(')');
        
        String s = sb.toString();
        return s;
	}
}