package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.player.CommandBalance;
import com.ToonBasic.blobcatraz.command.staff.CommandBan;
import com.ToonBasic.blobcatraz.command.staff.CommandFly;
import com.ToonBasic.blobcatraz.command.staff.CommandGamemode;
import com.ToonBasic.blobcatraz.command.staff.CommandTempBan;

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
        commands();
    }
    public void onDisable() {
        LOG.info("Now disabling Blobcatraz...");
    }
    
    public void commands() {
    //Staff Commands
        framework.registerCommand(new CommandBan());
        framework.registerCommand(new CommandFly());
        framework.registerCommand(new CommandGamemode());
        framework.registerCommand(new CommandTempBan());
    //Player Commands
        framework.registerCommand(new CommandBalance());
    //Register All    
        framework.registerCommands();
    }
}