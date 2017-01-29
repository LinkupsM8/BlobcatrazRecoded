package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.player.CommandBalance;
import com.ToonBasic.blobcatraz.command.player.CommandNickname;
import com.ToonBasic.blobcatraz.command.staff.CommandBan;
import com.ToonBasic.blobcatraz.command.staff.CommandFly;
import com.ToonBasic.blobcatraz.command.staff.CommandFreeze;
import com.ToonBasic.blobcatraz.command.staff.CommandGamemode;
import com.ToonBasic.blobcatraz.command.staff.CommandItem;
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
        events();
    }
    public void onDisable() {
        LOG.info("Now disabling Blobcatraz...");
    }
    
    public void commands() {
    //Staff Commands
        framework.registerCommand(new CommandBan());
        framework.registerCommand(new CommandFly());
        framework.registerCommand(new CommandFreeze());
        framework.registerCommand(new CommandGamemode());
        framework.registerCommand(new CommandItem());
        framework.registerCommand(new CommandTempBan());
    //Player Commands
        framework.registerCommand(new CommandBalance());
        framework.registerCommand(new CommandNickname());
    //Register All    
        framework.registerCommands();
    }
    
    public void events() {
        PluginManager pm = Bukkit.getPluginManager();
    //Staff Events
        pm.registerEvents(new CommandFreeze(), this);
    //Player Events
        pm.registerEvents(new CommandNickname(), this);
    }
}