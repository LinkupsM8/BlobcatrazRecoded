package com.ToonBasic.blobcatraz;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.command.player.CommandAFK;
import com.ToonBasic.blobcatraz.command.player.CommandBack;
import com.ToonBasic.blobcatraz.command.player.CommandBalance;
import com.ToonBasic.blobcatraz.command.player.CommandBaltop;
import com.ToonBasic.blobcatraz.command.player.CommandBlock;
import com.ToonBasic.blobcatraz.command.player.CommandBlockChest;
import com.ToonBasic.blobcatraz.command.player.CommandDelHome;
import com.ToonBasic.blobcatraz.command.player.CommandEmojis;
import com.ToonBasic.blobcatraz.command.player.CommandHelp;
import com.ToonBasic.blobcatraz.command.player.CommandHome;
import com.ToonBasic.blobcatraz.command.player.CommandHomes;
import com.ToonBasic.blobcatraz.command.player.CommandHub;
import com.ToonBasic.blobcatraz.command.player.CommandIgnore;
import com.ToonBasic.blobcatraz.command.player.CommandKit;
import com.ToonBasic.blobcatraz.command.player.CommandKits;
import com.ToonBasic.blobcatraz.command.player.CommandMessage;
import com.ToonBasic.blobcatraz.command.player.CommandNickname;
import com.ToonBasic.blobcatraz.command.player.CommandPay;
import com.ToonBasic.blobcatraz.command.player.CommandPrefix;
import com.ToonBasic.blobcatraz.command.player.CommandReply;
import com.ToonBasic.blobcatraz.command.player.CommandRules;
import com.ToonBasic.blobcatraz.command.player.CommandSell;
import com.ToonBasic.blobcatraz.command.player.CommandSetHome;
import com.ToonBasic.blobcatraz.command.player.CommandStaff;
import com.ToonBasic.blobcatraz.command.player.CommandToggleScoreboard;
import com.ToonBasic.blobcatraz.command.player.CommandTpChoose;
import com.ToonBasic.blobcatraz.command.player.CommandTpa;
import com.ToonBasic.blobcatraz.command.player.CommandWarp;
import com.ToonBasic.blobcatraz.command.player.CommandWarps;
import com.ToonBasic.blobcatraz.command.player.CommandWorth;
import com.ToonBasic.blobcatraz.command.special.CommandBurn;
import com.ToonBasic.blobcatraz.command.special.CommandCenter;
import com.ToonBasic.blobcatraz.command.special.CommandChestToKit;
import com.ToonBasic.blobcatraz.command.special.CommandDelPortal;
import com.ToonBasic.blobcatraz.command.special.CommandItemHolo;
import com.ToonBasic.blobcatraz.command.special.CommandKitToChest;
import com.ToonBasic.blobcatraz.command.special.CommandMobEgg;
import com.ToonBasic.blobcatraz.command.special.CommandNuke;
import com.ToonBasic.blobcatraz.command.special.CommandPTime;
import com.ToonBasic.blobcatraz.command.special.CommandPWeather;
import com.ToonBasic.blobcatraz.command.special.CommandPortal;
import com.ToonBasic.blobcatraz.command.special.CommandSetPortal;
import com.ToonBasic.blobcatraz.command.special.CommandShhh;
import com.ToonBasic.blobcatraz.command.special.CommandSlimeCannon;
import com.ToonBasic.blobcatraz.command.staff.CommandAnvil;
import com.ToonBasic.blobcatraz.command.staff.CommandBan;
import com.ToonBasic.blobcatraz.command.staff.CommandChat;
import com.ToonBasic.blobcatraz.command.staff.CommandClearInventory;
import com.ToonBasic.blobcatraz.command.staff.CommandCreateItem;
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
import com.ToonBasic.blobcatraz.command.staff.CommandSetWorth;
import com.ToonBasic.blobcatraz.command.staff.CommandShowinv;
import com.ToonBasic.blobcatraz.command.staff.CommandSkull;
import com.ToonBasic.blobcatraz.command.staff.CommandSmite;
import com.ToonBasic.blobcatraz.command.staff.CommandSonic;
import com.ToonBasic.blobcatraz.command.staff.CommandSpeed;
import com.ToonBasic.blobcatraz.command.staff.CommandSpy;
import com.ToonBasic.blobcatraz.command.staff.CommandSudo;
import com.ToonBasic.blobcatraz.command.staff.CommandTPAll;
import com.ToonBasic.blobcatraz.command.staff.CommandTempBan;
import com.ToonBasic.blobcatraz.command.staff.CommandTop;
import com.ToonBasic.blobcatraz.command.staff.CommandVanish;
import com.ToonBasic.blobcatraz.command.staff.CommandWarn;
import com.ToonBasic.blobcatraz.command.staff.CommandWorkbench;
import com.ToonBasic.blobcatraz.compat.vault.BEconomy;
import com.ToonBasic.blobcatraz.config.ConfigHolo;
import com.ToonBasic.blobcatraz.config.CustomHologram;
import com.ToonBasic.blobcatraz.listener.ListenAntiVoid;
import com.ToonBasic.blobcatraz.listener.ListenAutoLapis;
import com.ToonBasic.blobcatraz.listener.ListenAutoPickup;
import com.ToonBasic.blobcatraz.listener.ListenChat;
import com.ToonBasic.blobcatraz.listener.ListenDeath;
import com.ToonBasic.blobcatraz.listener.ListenJoin;
import com.ToonBasic.blobcatraz.listener.ListenPortal;
import com.ToonBasic.blobcatraz.listener.ListenVote;
import com.ToonBasic.blobcatraz.listener.item.ListenChatItem;
import com.ToonBasic.blobcatraz.listener.item.ListenSonic;
import com.ToonBasic.blobcatraz.listener.sign.ListenEnchantSign;
import com.ToonBasic.blobcatraz.listener.sign.ListenRepairSign;
import com.ToonBasic.blobcatraz.listener.sign.ListenSellAll;
import com.ToonBasic.blobcatraz.listener.sign.ListenShopSign;
import com.ToonBasic.blobcatraz.listener.sign.ListenSignColor;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.ScoreboardUtil;
import com.ToonBasic.blobcatraz.utility.Util;
import com.ToonBasic.blobcatraz.utility.VaultUtil;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class Core extends JavaPlugin {
    public static Core instance;
    public static File folder;
    public static Logger LOG;
    CommandFramework framework;

    private static final Server SERVER = Bukkit.getServer();
    private static final PluginManager PM = SERVER.getPluginManager();
    private static final ServicesManager SM = SERVER.getServicesManager();
    
    public void onEnable() {
        instance = this;
        LOG = getLogger();
        folder = getDataFolder();
        if(PM.isPluginEnabled("Vault")) {
        	BEconomy be = new BEconomy();
        	Vault V = Vault.getPlugin(Vault.class);
        	ServicePriority SP = ServicePriority.Highest;
        	SM.register(Economy.class, be, V, SP);
        	VaultUtil.setup();
        }
        framework = new CommandFramework(instance);
        LOG.info("Now registering Blobcatraz...");
        ConfigurationSerialization.registerClass(CustomHologram.class);
        commands();
        events();
    }
    public void onDisable() {
        LOG.info("Now disabling Blobcatraz...");
    }
    
    public void commands() {
    //Staff Commands
        framework.registerCommands(
        	new CommandAnvil(),
        	new CommandBan(),
        	new CommandChat(),
        	new CommandClearInventory(),
        	new CommandCreateItem(),
        	new CommandDelKit(),
        	new CommandDelWarp(),
        	new CommandEconomy(),
        	new CommandEnchant(),
        	new CommandEnderChest(),
        	new CommandFireball(),
        	new CommandFly(),
        	new CommandFreeze(),
        	new CommandGamemode(),
        	new CommandGod(),
        	new CommandHeal(),
        	new CommandItem(),
        	new CommandLag(),
        	new CommandMobSpawn(),
        	new CommandMute(),
        	new CommandPowertool(),
        	new CommandRepair(),
        	new CommandSeen(),
        	new CommandSetKit(),
        	new CommandSetMOTD(),
        	new CommandSetWarp(),
        	new CommandSetWorth(),
        	new CommandShowinv(),
        	new CommandSkull(),
        	new CommandSmite(),
        	new CommandSonic(),
        	new CommandSpeed(),
        	new CommandSpy(),
        	new CommandSudo(),
        	new CommandTempBan(),
        	new CommandTop(),
        	new CommandTPAll(),
        	new CommandVanish(),
        	new CommandWarn(),
        	new CommandWorkbench()
        );
        
    //Player Commands
        framework.registerCommands(
        	new CommandAFK(),
        	new CommandBack(),
        	new CommandBalance(),
        	new CommandBaltop(),
        	new CommandBlock(),
        	new CommandBlockChest(),
        	new CommandDelHome(),
        	new CommandEmojis(),
        	new CommandHelp(),
        	new CommandHome(),
        	new CommandHomes(),
        	new CommandHub(),
        	new CommandIgnore(),
        	new CommandKit(),
        	new CommandKits(),
        	new CommandMessage(),
        	new CommandNickname(),
        	new CommandPay(),
        	new CommandPrefix(),
        	new CommandReply(),
        	new CommandRules(),
        	new CommandSell(),
        	new CommandSetHome(),
        	new CommandStaff(),
        	new CommandToggleScoreboard(),
        	new CommandTpa(),
        	new CommandTpChoose(),
        	new CommandWarp(),
        	new CommandWarps(),
        	new CommandWorth()
        );
        
    //Special Commands
        framework.registerCommands(
        	new CommandBurn(),
        	new CommandCenter(),
        	new CommandChestToKit(),
        	new CommandDelPortal(),
        	new CommandItemHolo(),
        	new CommandKitToChest(),
        	new CommandMobEgg(),
        	new CommandNuke(),
        	new CommandPortal(),
        	new CommandPTime(),
        	new CommandPWeather(),
        	new CommandSetPortal(),
        	new CommandShhh(),
        	new CommandSlimeCannon()
        );
        
     //Dependents
        if(PM.isPluginEnabled("HolographicDisplays")) {
        	framework.registerCommand(new CommandItemHolo());
        	ConfigurationSerialization.registerClass(CustomHologram.class);
        	ConfigHolo.load();
        }
        
    //Register All    
        framework.registerCommands();
    }
    
    public void events() {
    	ItemUtil.load();
    	ScoreboardUtil.enable();
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
            new CommandIgnore(),
            new CommandMute(),
            new CommandSeen(),
            new CommandPowertool(),
            new CommandKits(),
            new CommandWarps(),

    		//Listener Events
    		new ListenAntiVoid(),
    		new ListenAutoLapis(),
    		new ListenChat(),
    		new ListenJoin(),
    		new ListenSignColor(),
    		new ListenPortal(),
    		new ListenSellAll(),
    		new ListenShopSign(),
    		new ListenEnchantSign(),
    		new ListenRepairSign(),
    		new ListenVote(),
    		new ListenDeath(),
    		new ListenAutoPickup(),
    		new ListenSonic(),
    		new ListenChatItem()
    	);
    }
}