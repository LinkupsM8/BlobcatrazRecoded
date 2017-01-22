package com.ToonBasic.blobcatraz;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.staff.CommandFly;
import com.ToonBasic.blobcatraz.command.staff.CommandGamemode;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {
    public static Core instance;

    CommandFramework framework;

    public void onEnable() {
        instance = this;
        framework = new CommandFramework(instance);
        getLogger().info("Now registering Blobcatraz...");
        run();
    }
    public void onDisable() {
        getLogger().info("Now disabling Blobcatraz...");
    }
    public void run() {
        framework.registerCommand(new CommandFly());
        framework.registerCommand(new CommandGamemode());
        framework.registerCommands();
    }
}