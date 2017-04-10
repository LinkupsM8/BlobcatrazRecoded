package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import com.ToonBasic.blobcatraz.command.player.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.special.CommandCenter;
import com.ToonBasic.blobcatraz.command.special.CommandChestToKit;
import com.ToonBasic.blobcatraz.command.special.CommandDelPortal;
import com.ToonBasic.blobcatraz.command.special.CommandKitToChest;
import com.ToonBasic.blobcatraz.command.special.CommandNuke;
import com.ToonBasic.blobcatraz.command.special.CommandPortal;
import com.ToonBasic.blobcatraz.command.special.CommandSetPortal;
import com.ToonBasic.blobcatraz.command.special.CommandShhh;
import com.ToonBasic.blobcatraz.command.staff.CommandAnvil;
import com.ToonBasic.blobcatraz.command.staff.CommandBan;
import com.ToonBasic.blobcatraz.command.staff.CommandChat;
import com.ToonBasic.blobcatraz.command.staff.CommandClearInventory;
import com.ToonBasic.blobcatraz.command.staff.CommandDelKit;
import com.ToonBasic.blobcatraz.command.staff.CommandDelWarp;
import com.ToonBasic.blobcatraz.command.staff.CommandEconomy;
import com.ToonBasic.blobcatraz.command.staff.CommandEnchant;
import com.ToonBasic.blobcatraz.command.staff.CommandEnderChest;
import com.ToonBasic.blobcatraz.command.staff.CommandFireball;
import com.ToonBasic.blobcatraz.command.staff.CommandFly;
import com.ToonBasic.blobcatraz.command.staff.CommandFreeze;
import com.ToonBasic.blobcatraz.command.staff.CommandGamemode;
import com.ToonBasic.blobcatraz.command.staff.CommandGod;
import com.ToonBasic.blobcatraz.command.staff.CommandHeal;
import com.ToonBasic.blobcatraz.command.staff.CommandItem;
import com.ToonBasic.blobcatraz.command.staff.CommandLag;
import com.ToonBasic.blobcatraz.command.staff.CommandMobSpawn;
import com.ToonBasic.blobcatraz.command.staff.CommandMute;
import com.ToonBasic.blobcatraz.command.staff.CommandPowertool;
import com.ToonBasic.blobcatraz.command.staff.CommandRepair;
import com.ToonBasic.blobcatraz.command.staff.CommandSeen;
import com.ToonBasic.blobcatraz.command.staff.CommandSetKit;
import com.ToonBasic.blobcatraz.command.staff.CommandSetMOTD;
import com.ToonBasic.blobcatraz.command.staff.CommandSetWarp;
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
import com.ToonBasic.blobcatraz.compat.vault.BEconomy;
import com.ToonBasic.blobcatraz.listener.ListenAntiVoid;
import com.ToonBasic.blobcatraz.listener.ListenChat;
import com.ToonBasic.blobcatraz.listener.ListenPortal;
import com.ToonBasic.blobcatraz.listener.ListenShopSign;
import com.ToonBasic.blobcatraz.listener.ListenSignColor;
import com.ToonBasic.blobcatraz.listener.ListenVote;
import com.ToonBasic.blobcatraz.listener.item.ListenSonic;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

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
        if(Bukkit.getPluginManager().isPluginEnabled("Vault")) {
        	BEconomy be = new BEconomy();
        	Vault V = Vault.getPlugin(Vault.class);
        	ServicePriority SP = ServicePriority.Highest;
        	Bukkit.getServicesManager().register(Economy.class, be, V, SP);
        }
    }
    public void onDisable() {
        LOG.info("Now disabling Blobcatraz...");
    }
    
    public void commands() {
    //Staff Commands
        framework.registerCommand(new CommandAnvil());
        framework.registerCommand(new CommandBan());
        framework.registerCommand(new CommandClearInventory());
        framework.registerCommand(new CommandDelKit());
        framework.registerCommand(new CommandDelWarp());
        framework.registerCommand(new CommandEconomy());
        framework.registerCommand(new CommandEnchant());
        framework.registerCommand(new CommandEnderChest());
        framework.registerCommand(new CommandFireball());
        framework.registerCommand(new CommandFly());
        framework.registerCommand(new CommandFreeze());
        framework.registerCommand(new CommandGamemode());
        framework.registerCommand(new CommandGod());
        framework.registerCommand(new CommandHeal());
        framework.registerCommand(new CommandItem());
        framework.registerCommand(new CommandLag());
        framework.registerCommand(new CommandMobSpawn());
        framework.registerCommand(new CommandMute());
        framework.registerCommand(new CommandPowertool());
        framework.registerCommand(new CommandRepair());
        framework.registerCommand(new CommandSeen());
        framework.registerCommand(new CommandSetKit());
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
    //Player Commands
        framework.registerCommand(new CommandAFK());
        framework.registerCommand(new CommandBack());
        framework.registerCommand(new CommandBalance());
        framework.registerCommand(new CommandBaltop());
        framework.registerCommand(new CommandEmojis());
        framework.registerCommand(new CommandHelp());
        framework.registerCommand(new CommandHub());
        framework.registerCommand(new CommandKit());
        framework.registerCommand(new CommandNickname());
        framework.registerCommand(new CommandPay());
        framework.registerCommand(new CommandPrefix());
        framework.registerCommand(new CommandRename());
        framework.registerCommand(new CommandRules());
        framework.registerCommand(new CommandWarp());
        framework.registerCommand(new CommandChat());
        framework.registerCommand(new CommandStaff());
        framework.registerCommand(new CommandHome());
        framework.registerCommand(new CommandHomes());
        framework.registerCommand(new CommandSetHome());
        framework.registerCommand(new CommandDelHome());
        framework.registerCommand(new CommandMSG());
        framework.registerCommand(new CommandReply());
    //Special Commands
        framework.registerCommand(new CommandCenter());
        framework.registerCommand(new CommandChestToKit());
        framework.registerCommand(new CommandDelPortal());
        framework.registerCommand(new CommandKitToChest());
        framework.registerCommand(new CommandNuke());
        framework.registerCommand(new CommandPortal());
        framework.registerCommand(new CommandSetPortal());
        framework.registerCommand(new CommandShhh());
    //Register All    
        framework.registerCommands();
    }
    
    public void events() {
    	ItemUtil.load();
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
            new CommandSeen(),
            new CommandPowertool(),

    		//Listener Events
    		new ListenChat(),
    		new ListenSignColor(),
    		new ListenSonic(),
    		new ListenAntiVoid(),
    		new ListenPortal(),
    		new ListenShopSign(),
    		new ListenVote()
    	);
    }
}