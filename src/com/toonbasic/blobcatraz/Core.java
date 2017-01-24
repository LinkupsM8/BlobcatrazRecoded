package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.staff.CommandFly;
import com.ToonBasic.blobcatraz.command.staff.CommandGamemode;

public class Core extends JavaPlugin {
    public static Core instance;
    public static File folder;
    public static Logger LOG;

    CommandFramework framework;

    public void onEnable() {
        instance = this;
        LOG = getLogger();
        folder = getDataFolder();
        framework = new CommandFramework(instance);
        LOG.info("Now registering Blobcatraz...");
        run();
    }
    public void onDisable() {
        LOG.info("Now disabling Blobcatraz...");
    }
    public void run() {
        framework.registerCommand(new CommandFly());
        framework.registerCommand(new CommandGamemode());
        framework.registerCommands();
    }
}