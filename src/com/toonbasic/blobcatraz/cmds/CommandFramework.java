package com.toonbasic.blobcatraz.cmds;

import com.google.common.reflect.ClassPath;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.help.*;
import org.bukkit.plugin.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class CommandFramework {
    private final Plugin plugin;
    private final HashMap<ICommand, Plugin> cmds;
    private CommandMap map;
    private ArrayList<String> commands;

    public CommandFramework(Plugin plugin) {
        this.plugin = plugin;
        commands = new ArrayList<>();
        cmds = new HashMap<>();
        if (this.plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            SimplePluginManager manager = (SimplePluginManager) this.plugin.getServer().getPluginManager();
            try {
                Field field = null;
                try {
                    field = SimplePluginManager.class.getDeclaredField("commandMap");
                } catch (NoSuchFieldException | SecurityException e) {
                    e.printStackTrace();
                }
                if (field == null) {
                    return;
                }
                field.setAccessible(true);
                map = ((CommandMap) field.get(manager));
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldError | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized List<ICommand> addAll(String packageName) throws ClassNotFoundException, IOException {
        final ClassLoader loader = this.getClass().getClassLoader();
        List<ICommand> commands = new ArrayList<>();
        ClassPath.from(loader).getTopLevelClasses().forEach(info -> {
            if (info.getName().startsWith(packageName)) {
                final Class<?> clazz = info.load();
                if (clazz.getSuperclass() == ICommand.class) {
                    try {commands.add(((ICommand) clazz.newInstance()));
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        commands.forEach(this::registerCommand);
        return commands;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void registerCommand(ICommand cmd) {
        cmds.put(cmd, plugin);
    }

    public void registerCommands() {
        Set<HelpTopic> help = new TreeSet<>(HelpTopicComparator.helpTopicComparatorInstance());
        int i = 0;
        commands = new ArrayList<>();
        for (ICommand cmd : cmds.keySet()) {
            i++;
            commands.add(cmd.getCommand());

            if (cmd.getAliases() != null) {
                Arrays.asList(cmd.getAliases()).forEach(s -> commands.add(s));
            }
            BukkitCommand com = new BukkitCommand(cmd.getCommand(), cmd.getExecutor(), plugin);
            if (cmd.getAliases() != null) {
                com.setAliases(Arrays.asList(cmd.getAliases()));
            }
            if (cmd.getUsage() != null) {
                com.setUsage(cmd.getUsage());
            }

            com.setLabel(cmd.getCommand());
            com.setUsage("/" + cmd.getCommand() + " " + cmd.getUsage());
            com.setDescription(plugin.getName() + " " + cmd.getCommand() + " command.");
            map.register(plugin.getName(), com);
            Command command = map.getCommand(cmd.getCommand());
            HelpTopic topic = new GenericCommandHelpTopic(command);
            help.add(topic);
        }
        IndexHelpTopic indexTopic = new IndexHelpTopic(plugin.getName(), "All commands for " + plugin.getName(), null, help, "§6Below is a list of all " + plugin.getName() + " commands:");
        Bukkit.getServer().getHelpMap().addTopic(indexTopic);
        plugin.getLogger().info("[Blobcatraz] Registered " + i + " commands and " + commands.size() + " aliases");
    }
}