package com.ToonBasic.blobcatraz.command;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpMap;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import com.ToonBasic.blobcatraz.utility.Util;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;

public class CommandFramework {
    private static final Server SERVER = Bukkit.getServer();
    private static final PluginManager PLUGIN_MANAGER = SERVER.getPluginManager();
    private static final HelpMap HELP_MAP = SERVER.getHelpMap();

    private final Plugin plugin;
    private static Map<ICommand, Plugin> cmds;
    private CommandMap commandMap;
    private static List<String> commands;

    public CommandFramework(Plugin plugin) {
        this.plugin = plugin;
        commands = new ArrayList<String>();
        cmds = Maps.newHashMap();

        if (PLUGIN_MANAGER instanceof SimplePluginManager) {
            try {
                SimplePluginManager spm = (SimplePluginManager) PLUGIN_MANAGER;
                Class<? extends SimplePluginManager> c = spm.getClass();
                Field field = c.getDeclaredField("commandMap");
                field.setAccessible(true);
                commandMap = (CommandMap) field.get(spm);
            } catch (Exception ex) {
                String error = "CommandFramework Failure:";
                Util.print(error);
                ex.printStackTrace();
            }
        }
    }

    public synchronized List<ICommand> addAll(String packageName) throws ClassNotFoundException, IOException {
        Class<? extends CommandFramework> c = getClass();
        ClassLoader cl = c.getClassLoader();
        List<ICommand> commands = new ArrayList<ICommand>();
        ClassPath.from(cl).getTopLevelClasses().forEach(info -> {
            if (info.getName().startsWith(packageName)) {
                Class<?> clazz = info.load();
                if (clazz.getSuperclass() == ICommand.class) {
                    try {
                        ICommand ic = (ICommand) clazz.newInstance();
                        commands.add(ic);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        commands.forEach(this::registerCommand);
        return commands;
    }

    public List<String> getCommands() {return commands;}
    public void registerCommands(ICommand... ics) {for(ICommand ic : ics) registerCommand(ic);}
    public void registerCommand(ICommand ic) {cmds.put(ic, plugin);}

    public void registerCommands() {
        Set<HelpTopic> help = Sets.newTreeSet(HelpTopicComparator.helpTopicComparatorInstance());
        int i = 0;
        commands = new ArrayList<String>();
        for(ICommand ic : cmds.keySet()) {
            i++;
            commands.add(ic.getCommand());
            if(ic.getAliases() != null) {
                List<String> aliases = Arrays.asList(ic.getAliases());
                aliases.forEach(s -> commands.add(s));
            }
            BukkitCommand bc = new BukkitCommand(ic.getCommand(), ic.getExecutor(), plugin);
            if(ic.getAliases() != null) {
                bc.setAliases(Arrays.asList(ic.getAliases()));
            }
            if(ic.getUsage() != null) {
                bc.setUsage("/" + ic.getCommand() + " " + ic.getUsage());
            }

            bc.setLabel(ic.getCommand());
            bc.setDescription(plugin.getName() + " " + ic.getCommand() + " command.");
            commandMap.register(plugin.getName(), bc);
            Command cmd = commandMap.getCommand(ic.getCommand());
            HelpTopic ht = new GenericCommandHelpTopic(cmd);
            help.add(ht);
            
            if(ic instanceof Listener) {
            	Listener l = (Listener) ic;
            	Util.regEvents(plugin, l);
            }
        }
        IndexHelpTopic iht = new IndexHelpTopic(plugin.getName(), "All commands for " + plugin.getName(), null, help, "§6Below is a list of commands from " + plugin.getName());
        HELP_MAP.addTopic(iht);
        plugin.getLogger().info("Registered " + i + " commands and " + commands.size() + " aliases");
    }
}