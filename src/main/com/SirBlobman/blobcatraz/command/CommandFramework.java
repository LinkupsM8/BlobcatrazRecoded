package com.SirBlobman.blobcatraz.command;

import com.SirBlobman.blobcatraz.utility.Util;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.event.Listener;
import org.bukkit.help.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class CommandFramework {
	private static final Server SERVER = Bukkit.getServer();
	private static final PluginManager PM = SERVER.getPluginManager();
	private static final HelpMap HM = SERVER.getHelpMap();
	private static Map<ICommand, Plugin> cmds;
	private static List<String> commands;
	
	private final Plugin pl;
	private CommandMap cMap;
	private Map<String, Command> known;
	
	@SuppressWarnings("unchecked")
	public CommandFramework(Plugin pl) {
		this.pl = pl;
		commands = Util.newList();
		cmds = Util.newMap();
		
		if(PM instanceof SimplePluginManager) {
			try {
				SimplePluginManager spm = (SimplePluginManager) PM;
				Class<? extends SimplePluginManager> clazz = spm.getClass();
				Field f = clazz.getDeclaredField("commandMap");
				f.setAccessible(true);
				Object o = f.get(spm);
				cMap = (CommandMap) o;
				if(cMap instanceof SimpleCommandMap) {
					try {
						SimpleCommandMap scm = (SimpleCommandMap) cMap;
						Class<? extends SimpleCommandMap> clazz2 = scm.getClass();
						Field f2 = clazz2.getDeclaredField("knownCommands");
						f2.setAccessible(true);
						Object o2 = f2.get(scm);
						known = (Map<String, Command>) o2;
					} catch(Throwable ex) {
						String error = "CommandFramework Failure:";
						Util.print(error);
						ex.printStackTrace();
					}
				}
			} catch(Throwable ex) {
				String error = "CommandFramework Failure:";
				Util.print(error);
				ex.printStackTrace();
			}
		}
	}
	
	public synchronized List<ICommand> addAll(String pName) throws ClassNotFoundException, IOException {
		Class<? extends CommandFramework> clazz = getClass();
		ClassLoader cl = clazz.getClassLoader();
		List<ICommand> list = Util.newList();
		ClassPath cp = ClassPath.from(cl);
		Set<ClassInfo> set = cp.getTopLevelClasses();
		set.forEach(info -> {
			String name = info.getName();
			if(name.startsWith(pName)) {
				Class<?> c = info.load();
				Class<?> sup = c.getSuperclass();
				if(sup == ICommand.class) {
					try {
						ICommand ic = (ICommand) c.newInstance();
						list.add(ic);
					} catch(Throwable ex) {ex.printStackTrace();}
				} else if(sup == PlayerCommand.class) {
					try {
						PlayerCommand pc = (PlayerCommand) c.newInstance();
						list.add(pc);
					} catch(Throwable ex) {ex.printStackTrace();}
				}
			}
		});
		
		list.forEach(ic -> registerCommand(ic));
		return list;
	}
	
	public List<String> getCommands() {return commands;}
	public void registerCommands(ICommand... ics) {for(ICommand ic: ics) {registerCommand(ic);}}
	public void registerCommand(ICommand ic) {cmds.put(ic, pl);}
	
	public void registerCommands() {
		String pname = pl.getName();
		HelpTopicComparator htc = HelpTopicComparator.helpTopicComparatorInstance();
		Set<HelpTopic> set = Sets.newTreeSet(htc);
		int i = 0;
		commands = Util.newList();
		Set<ICommand> ics = cmds.keySet();
		for(ICommand ic : ics) {
			i++;
			String cmd = ic.getCommand();
			CommandExecutor cx = ic.getExecutor();
			commands.add(cmd);
			
			BlobcatrazCommand bc = new BlobcatrazCommand(cmd, cx, pl);
			String[] aliases = ic.getAliases();
			if(aliases != null) {
				List<String> list = Util.newList(aliases);
				list.forEach(s -> {
					if(known.containsKey(s)) known.remove(s);
					commands.add(s);
				});
				bc.setAliases(list);
			}
			
			String use = ic.getUsage();
			if(use != null) {bc.setUsage("/" + cmd + " " + use);}
			bc.setLabel(cmd);
			bc.setDescription(pname + " " + cmd + " command.");
			if(known.containsKey(cmd)) {
				Command c = known.get(cmd);
				String cname = c.getName();
				String msg = Util.format("Attempting to override the previously registered command '%1s'", cname);
				Util.print(msg);
				known.remove(cmd);
			}
			
			boolean reg = cMap.register(cmd, pname, bc);
			if(reg) {		
				Command c = cMap.getCommand(cmd);
				HelpTopic ht = new GenericCommandHelpTopic(c);
				set.add(ht);
				
				if(ic instanceof TabCompleter) {
					TabCompleter tc = (TabCompleter) ic;
					bc.setTabCompleter(tc);
				}
				
				if(ic instanceof Listener) {
					Listener l = (Listener) ic;
					Util.regEvents(pl, l);
				}
			} else {
				String error = Util.format("'%1s' could not override the command '%2s'", pname, cmd);
				Util.print(error);
			}
		}
		
		IndexHelpTopic iht = new IndexHelpTopic(pname, "All commands for " + pname, null, set, Util.color("&6Below is a list of commands from " + pname));
		HM.addTopic(iht);
		Logger l = pl.getLogger();
		l.info("Registered " + i + " commands and " + commands.size() + " aliases");
	}
}