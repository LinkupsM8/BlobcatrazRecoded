package com.ToonBasic.blobcatraz.utility;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;

//import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultUtil extends Util {
	private static final Server SERVER = Bukkit.getServer();
	private static final ServicesManager SM = SERVER.getServicesManager();
	private static Economy econ = null;
	private static Permission perm = null;
	//private static Chat chat = null;
	
	public static void setup() {
		RegisteredServiceProvider<Economy> e = SM.getRegistration(Economy.class);
		RegisteredServiceProvider<Permission> p = SM.getRegistration(Permission.class);
		//RegisteredServiceProvider<Chat> c = SM.getRegistration(Chat.class);
		econ = e.getProvider();
		perm = p.getProvider();
		//chat = c.getProvider();
	}
	
	public static String mainRank(Player p) {
		if(perm != null) {
			String rank = perm.getPrimaryGroup(p);
			return rank;
		} else return color("&4ERROR");
	}
	
	public static String[] groups(Player p) {
		if(perm != null) {
			String[] groups = perm.getPlayerGroups(p);
			return groups;
		} else {
			String[] groups = color("&4&lFailed", "&4&lto get", "&4&lGroups!");
			return groups;
		}
	}
	
	public static double balance(Player p) {
		if(econ != null) {
			double balance = econ.getBalance(p);
			return balance;
		} else {
			double balance = ConfigDatabase.balance(p);
			return balance;
		}
	}
}