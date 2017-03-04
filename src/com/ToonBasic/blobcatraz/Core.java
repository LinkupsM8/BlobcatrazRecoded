package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.player.CommandAFK;
import com.ToonBasic.blobcatraz.command.player.CommandBalance;
import com.ToonBasic.blobcatraz.command.player.CommandEmojis;
import com.ToonBasic.blobcatraz.command.player.CommandHub;
import com.ToonBasic.blobcatraz.command.player.CommandNickname;
import com.ToonBasic.blobcatraz.command.player.CommandRename;
import com.ToonBasic.blobcatraz.command.staff.CommandAnvil;
import com.ToonBasic.blobcatraz.command.staff.CommandBan;
import com.ToonBasic.blobcatraz.command.staff.CommandClearInventory;
import com.ToonBasic.blobcatraz.command.staff.CommandEnderChest;
import com.ToonBasic.blobcatraz.command.staff.CommandFly;
import com.ToonBasic.blobcatraz.command.staff.CommandFreeze;
import com.ToonBasic.blobcatraz.command.staff.CommandGamemode;
import com.ToonBasic.blobcatraz.command.staff.CommandHeal;
import com.ToonBasic.blobcatraz.command.staff.CommandItem;
import com.ToonBasic.blobcatraz.command.staff.CommandLag;
import com.ToonBasic.blobcatraz.command.staff.CommandMobSpawn;
import com.ToonBasic.blobcatraz.command.staff.CommandRepair;
import com.ToonBasic.blobcatraz.command.staff.CommandShowinv;
import com.ToonBasic.blobcatraz.command.staff.CommandSkull;
import com.ToonBasic.blobcatraz.command.staff.CommandSmite;
import com.ToonBasic.blobcatraz.command.staff.CommandSonic;
import com.ToonBasic.blobcatraz.command.staff.CommandSpeed;
import com.ToonBasic.blobcatraz.command.staff.CommandSpy;
import com.ToonBasic.blobcatraz.command.staff.CommandSudo;
import com.ToonBasic.blobcatraz.command.staff.CommandTPAll;
import com.ToonBasic.blobcatraz.command.staff.CommandTempBan;
import com.ToonBasic.blobcatraz.command.staff.CommandVanish;
import com.ToonBasic.blobcatraz.command.staff.CommandWorkbench;
import com.ToonBasic.blobcatraz.listener.ListenSonic;

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
        framework.registerCommand(new CommandHeal());
        framework.registerCommand(new CommandItem());
        framework.registerCommand(new CommandLag());
        framework.registerCommand(new CommandTempBan());
        framework.registerCommand(new CommandVanish());
        framework.registerCommand(new CommandMobSpawn());
        framework.registerCommand(new CommandShowinv());
        framework.registerCommand(new CommandSkull());
        framework.registerCommand(new CommandRepair());
        framework.registerCommand(new CommandSpeed());
        framework.registerCommand(new CommandClearInventory());
        framework.registerCommand(new CommandSmite());
        framework.registerCommand(new CommandWorkbench());
        framework.registerCommand(new CommandAnvil());
        framework.registerCommand(new CommandEnderChest());
        framework.registerCommand(new CommandSonic());
        framework.registerCommand(new CommandSpy());
        framework.registerCommand(new CommandSudo());
		framework.registerCommand(new CommandTPAll());
    //Player Commands
        framework.registerCommand(new CommandAFK());
        framework.registerCommand(new CommandBalance());
        framework.registerCommand(new CommandEmojis());
        framework.registerCommand(new CommandHub());
        framework.registerCommand(new CommandNickname());
        framework.registerCommand(new CommandRename());
    //Register All    
        framework.registerCommands();
    }
    
    public void events() {
        PluginManager pm = Bukkit.getPluginManager();
    //Staff Events
        pm.registerEvents(new CommandFreeze(), this);
        pm.registerEvents(new CommandVanish(), this);
        pm.registerEvents(new CommandShowinv(), this);
        pm.registerEvents(new ListenSonic(), this);
        pm.registerEvents(new CommandSpy(), this);
    //Player Events
        pm.registerEvents(new CommandAFK(), this);
        pm.registerEvents(new CommandNickname(), this);
        pm.registerEvents(new CommandEmojis(), this);
    }
}