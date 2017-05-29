package com.SirBlobman.blobcatraz.economy;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

import com.SirBlobman.blobcatraz.economy.command.player.CommandBalance;
import com.SirBlobman.blobcatraz.economy.command.player.CommandBaltop;
import com.SirBlobman.blobcatraz.economy.command.player.CommandPay;
import com.SirBlobman.blobcatraz.economy.command.player.CommandSell;
import com.SirBlobman.blobcatraz.economy.command.player.CommandWorth;
import com.SirBlobman.blobcatraz.economy.command.staff.CommandEconomy;
import com.SirBlobman.blobcatraz.economy.command.staff.CommandSetWorth;
import com.SirBlobman.blobcatraz.economy.compat.vault.BEconomy;
import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.utility.Util;
import com.ToonBasic.blobcatraz.utility.VaultUtil;

import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;

public class EconomyCore extends Core {
	private static final Server SERVER = Bukkit.getServer();
	private static final PluginManager PM = SERVER.getPluginManager();
	private static final ServicesManager SM = SERVER.getServicesManager();
	
	public static EconomyCore instance;
	CommandFramework framework;
	
	@Override
	public void onEnable() {
		instance = this;
        if(PM.isPluginEnabled("Vault")) {
        	BEconomy be = new BEconomy();
        	Vault V = Vault.getPlugin(Vault.class);
        	ServicePriority SP = ServicePriority.Highest;
        	SM.register(Economy.class, be, V, SP);
        	VaultUtil.setup();
        } 
		framework = new CommandFramework(this);
		ecoCommands();
		Util.print("&2Economy addon enabled.");
	}
	
	@Override
	public void onDisable() {
		Util.print("&4Economy addon disabled.");
	}
	
	private void ecoCommands() {
		framework.registerCommands(
			//Player
			new CommandBalance(),
			new CommandBaltop(),
			new CommandPay(),
			new CommandSell(),
			new CommandWorth(),
			
			//Staff
			new CommandEconomy(),
			new CommandSetWorth()
		);
		framework.registerCommands();
	}
}