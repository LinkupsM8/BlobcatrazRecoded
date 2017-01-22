package com.toonbasic.blobcatraz;

import com.toonbasic.blobcatraz.cmds.BukkitCommand;
import com.toonbasic.blobcatraz.cmds.CommandFramework;
import com.toonbasic.blobcatraz.cmds.staff.FlyCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class Core extends JavaPlugin {
    public static Core instance;

    CommandFramework framework = new CommandFramework(instance);

    public void onEnable() {
        instance = this;
        getLogger().info("Now loading Blobcatraz...");
    }

    public void onDisable() {
        getLogger().info("Now disabling Blobcatraz...");
    }
    public void run() {
        framework.registerCommand(new FlyCommand());
        framework.registerCommands();
    }
}