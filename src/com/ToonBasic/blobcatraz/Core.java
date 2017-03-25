package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import com.ToonBasic.blobcatraz.command.staff.*;
import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.player.CommandAFK;
import com.ToonBasic.blobcatraz.command.player.CommandBack;
import com.ToonBasic.blobcatraz.command.player.CommandBalance;
import com.ToonBasic.blobcatraz.command.player.CommandBaltop;
import com.ToonBasic.blobcatraz.command.player.CommandEmojis;
import com.ToonBasic.blobcatraz.command.player.CommandHelp;
import com.ToonBasic.blobcatraz.command.player.CommandHub;
import com.ToonBasic.blobcatraz.command.player.CommandNickname;
import com.ToonBasic.blobcatraz.command.player.CommandPay;
import com.ToonBasic.blobcatraz.command.player.CommandPrefix;
import com.ToonBasic.blobcatraz.command.player.CommandRename;
import com.ToonBasic.blobcatraz.command.player.CommandWarp;
import com.ToonBasic.blobcatraz.command.special.CommandNuke;
import com.ToonBasic.blobcatraz.listener.ListenAntiVoid;
import com.ToonBasic.blobcatraz.listener.ListenChat;
import com.ToonBasic.blobcatraz.listener.ListenSignColor;
import com.ToonBasic.blobcatraz.listener.ListenSonic;
import com.ToonBasic.blobcatraz.utility.Util;

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
        framework.registerCommand(new CommandAnvil());
        framework.registerCommand(new CommandBan());
        framework.registerCommand(new CommandClearInventory());
        framework.registerCommand(new CommandDelWarp());
        framework.registerCommand(new CommandEconomy());
        framework.registerCommand(new CommandEnchant());
        framework.registerCommand(new CommandEnderChest());
        framework.registerCommand(new CommandFireball());
        framework.registerCommand(new CommandFly());
        framework.registerCommand(new CommandFreeze());
        framework.registerCommand(new CommandGamemode());
        framework.registerCommand(new CommandHeal());
        framework.registerCommand(new CommandItem());
        framework.registerCommand(new CommandLag());
        framework.registerCommand(new CommandMobSpawn());
        framework.registerCommand(new CommandRepair());
        framework.registerCommand(new CommandSetMOTD());
        framework.registerCommand(new CommandSetWarp());
        framework.registerCommand(new CommandShowinv());
        framework.registerCommand(new CommandSkull());
        framework.registerCommand(new CommandSpeed());
        framework.registerCommand(new CommandSmite());
        framework.registerCommand(new CommandSonic());
        framework.registerCommand(new CommandSpy());
        framework.registerCommand(new CommandSudo());
        framework.registerCommand(new CommandTempBan());
		framework.registerCommand(new CommandTPAll());
        framework.registerCommand(new CommandVanish());
        framework.registerCommand(new CommandWorkbench());
        framework.registerCommand(new CommandGod());
        framework.registerCommand(new CommandMute());
    //Player Commands
        framework.registerCommand(new CommandAFK());
        framework.registerCommand(new CommandBack());
        framework.registerCommand(new CommandBalance());
        framework.registerCommand(new CommandBaltop());
        framework.registerCommand(new CommandEmojis());
        framework.registerCommand(new CommandHelp());
        framework.registerCommand(new CommandHub());
        framework.registerCommand(new CommandNickname());
        framework.registerCommand(new CommandPay());
        framework.registerCommand(new CommandPrefix());
        framework.registerCommand(new CommandRename());
        framework.registerCommand(new CommandWarp());
    //Special Commands
        framework.registerCommand(new CommandNuke());
    //Register All    
        framework.registerCommands();
    }
    
    public void events() {
    	Util.regEvents(
    		//Command Events
    		new CommandBack(),
    		new CommandFreeze(),
    		new CommandVanish(),
    		new CommandSetMOTD(),
    		new CommandShowinv(),
    		new CommandSpy(),
    		new CommandAFK(),
    		new CommandNickname(),
    		new CommandEmojis(),
            new CommandGod(),
            new CommandMute(),

    		//Listener Events
    		new ListenChat(),
    		new ListenSignColor(),
    		new ListenSonic(),
    		new ListenAntiVoid()
    	);
    }
}