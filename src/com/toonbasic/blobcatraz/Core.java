package com.ToonBasic.blobcatraz;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.staff.CommandFly;
import com.ToonBasic.blobcatraz.command.staff.CommandGamemode;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Core extends JavaPlugin {
    public static Core instance;
    public static Logger LOG;

    CommandFramework framework;

    public void onEnable() {
        instance = this;
        LOG = getLogger();
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