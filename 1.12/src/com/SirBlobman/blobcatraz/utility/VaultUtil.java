package com.SirBlobman.blobcatraz.utility;


import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class VaultUtil extends Util {
	private static final ServicesManager SM = SERVER.getServicesManager();
	private static Economy ECON = null;
	private static Permission PERM = null;
	
	public static void setup() {
		RegisteredServiceProvider<Economy> econ = SM.getRegistration(Economy.class);
		RegisteredServiceProvider<Permission> perm = SM.getRegistration(Permission.class);
		ECON = econ.getProvider();
		PERM = perm.getProvider();
	}
	
	public static String mainRank(Player p) {
		if(PERM != null && PERM.hasGroupSupport()) {
			try {
				String rank = PERM.getPrimaryGroup(p);
				return rank;
			} catch(Throwable ex) {
				String rank = color("&4Error");
				return rank;
			}
		} else {
			String rank = color("&4Error");
			return rank;
		}
	}
	
	public static String[] groups(Player p) {
		if(PERM != null) {
			String[] groups = PERM.getPlayerGroups(p);
			return groups;
		} else {
			String[] groups = color("&4&lFailed to", "&4&lget groups!");
			return groups;
		}
	}
	
	public static String nextRank(Player p, String o) {
		o = o.toUpperCase();
		List<Character> list = newList(
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
			'U', 'V', 'W', 'X', 'Y', 'Z'
		);
		if(o.length() > 1) {return "None";}
		char f = o.charAt(0);
		int i = list.indexOf(f);
		int n = i + 1;
		try {
			char c = list.get(n);
			String s = Character.toString(c);
			return s;
		} catch(Throwable ex) {return "None";}
	}
	
	public static double balance(Player p) {
		if(ECON != null) {
			double bal = ECON.getBalance(p);
			return bal;
		} else {
			double bal = ConfigDatabase.balance(p);
			return bal;
		}
	}
}