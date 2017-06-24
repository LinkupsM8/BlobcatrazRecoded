package com.SirBlobman.blobcatraz;

import com.SirBlobman.blobcatraz.command.CommandFramework;
import com.SirBlobman.blobcatraz.command.player.*;
import com.SirBlobman.blobcatraz.command.special.*;
import com.SirBlobman.blobcatraz.command.staff.*;
import com.SirBlobman.blobcatraz.compat.BPlaceholders;
import com.SirBlobman.blobcatraz.listener.*;
import com.SirBlobman.blobcatraz.listener.sign.*;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.VaultUtil;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Core extends JavaPlugin {
	private static final Server SERVER = Bukkit.getServer();
	private static final PluginManager PM = SERVER.getPluginManager();
	
	public static Core INSTANCE;
	public static File FOLDER;
	private static CommandFramework CF;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		FOLDER = getDataFolder();
		CF = new CommandFramework(INSTANCE);
		ItemUtil.load();
		commands();
		events();
		Util.broadcast("&9Blobcatraz &ais now enabled!");
	}
	
	@Override
	public void onDisable() {
		Util.broadcast("&9Blobcatraz &cis now disabled!");
	}
	
	private void commands() {
		//Player
		CF.registerCommands(
			new CommandAFK(), new CommandBack(), new CommandBlock(), new CommandBlockChest(),
			new CommandDeleteHome(), new CommandEmojis(), new CommandHome(), new CommandHomes(),
			new CommandHub(), new CommandIgnore(), new CommandKit(), new CommandKits(),
			new CommandMessage(), new CommandNickname(), new CommandReply(), new CommandReport(),
			new CommandRules(), new CommandSetHome(), new CommandStaffList(), new CommandStats(),
			new CommandToggleScoreboard(), new CommandTPA(), new CommandTPAHere(), new CommandTPChoose(),
			new CommandVote(), new CommandWarp(), new CommandWarps()
		);
		
		//Staff
		CF.registerCommands(
			new CommandBan(), new CommandBroadcast(), new CommandChatControl(), new CommandClearInventory(),
			new CommandCreateKit(), new CommandCreateWarp(), new CommandDeleteKit(), new CommandDeleteWarp(),
			new CommandEnchant(), new CommandFly(), new CommandFreeze(), new CommandGameMode(),
			new CommandGod(), new CommandHeal(), new CommandInfo(), new CommandItem(), 
			new CommandLag(), new CommandMobSpawn(), new CommandMute(), new CommandPrefix(),
			new CommandRealName(), new CommandSeen(), new CommandSetHub(), new CommandSetMOTD(),
			new CommandShowInv(), new CommandSmite(), new CommandSpeed(), new CommandSpy(),
			new CommandSudo(), new CommandTeleport(), new CommandTempBan(), new CommandTop(),
			new CommandTPAll(), new CommandVanish(), new CommandWarning()
		);
		
		//Special
		CF.registerCommands(
			new CommandAnvil(), new CommandBlockData(), new CommandCreateItem(), new CommandEnderChest(),
			new CommandPowerTool(), new CommandProjectile(), new CommandRepair(), new CommandSkull(),
			new CommandSonic(), new CommandWorkbench()
		);
		
		if(PM.isPluginEnabled("Votifier")) {
			Util.regEvents(this, new ListenVote());
			CF.registerCommand(new CommandFakeVote());
		}
		
		if(PM.isPluginEnabled("PlaceholderAPI")) {
			BPlaceholders bp = new BPlaceholders();
			bp.hook();
		}
		
		if(PM.isPluginEnabled("Vault")) VaultUtil.setup();
		
		CF.registerCommands();
	}
	
	private void events() {
		Util.regEvents(this,
			new ListenAntiVoid(), new ListenAutoLapis(), new ListenAutoPickup(),
			new ListenChat(), new ListenJoin(), new ListenPortal(),
			
			new ListenEnchantSign(), new ListenRepairSign(), new ListenSellAll(),
			new ListenShopSign(), new ListenSign(), new ListenSignColor()
		);
	}
}