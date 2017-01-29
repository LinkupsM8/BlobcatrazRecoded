package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import com.ToonBasic.blobcatraz.command.staff.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.player.CommandAFK;
import com.ToonBasic.blobcatraz.command.player.CommandBalance;
import com.ToonBasic.blobcatraz.command.player.CommandNickname;
import com.ToonBasic.blobcatraz.command.player.warp.Warp;

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
        configs();
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
        framework.registerCommand(new CommandVanish());
    //Player Commands
        framework.registerCommand(new CommandAFK());
        framework.registerCommand(new CommandBalance());
        framework.registerCommand(new CommandNickname());
    //Register All    
        framework.registerCommands();
    }
    
    public void events() {
        PluginManager pm = Bukkit.getPluginManager();
    //Staff Events
        pm.registerEvents(new CommandFreeze(), this);
        pm.registerEvents(new CommandVanish(), this);
    //Player Events
        pm.registerEvents(new CommandAFK(), this);
        pm.registerEvents(new CommandNickname(), this);
    }
    
    public void configs() {
    	ConfigurationSerialization.registerClass(Warp.class);
    }
}