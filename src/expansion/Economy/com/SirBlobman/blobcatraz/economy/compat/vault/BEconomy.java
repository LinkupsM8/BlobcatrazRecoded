package com.SirBlobman.blobcatraz.economy.compat.vault;

import com.SirBlobman.blobcatraz.config.ConfigDatabase;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.text.NumberFormat;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

//@FormatterOff
public class BEconomy implements Economy {
	public boolean isEnabled() {return true;}
	public String getName() {return "Blobcatraz";}
	public boolean hasBankSupport() {return false;}
	public int fractionalDigits() {return -1;}
	public String currencyNamePlural() {return "$";}
	public String currencyNameSingular() {return "$";}
	public String format(double amount) {
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		String fo = nf.format(amount);
		return fo;
	}

    @SuppressWarnings("deprecation")
	private OfflinePlayer getPlayer(String name) {
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);
		return op;
	}
	
	public boolean hasAccount(OfflinePlayer op) {
		boolean has = ConfigDatabase.exists(op);
		return has;
	}
	public boolean hasAccount(OfflinePlayer op, String world) {return hasAccount(op);}
	public boolean hasAccount(String name, String world) {return hasAccount(name);}
	public boolean hasAccount(String name) {return hasAccount(getPlayer(name));}
	
	public double getBalance(OfflinePlayer op) {
		double bal = ConfigDatabase.balance(op);
		return bal;
	}
	public double getBalance(OfflinePlayer op, String world) {return getBalance(op);}
	public double getBalance(String name, String world) {return getBalance(name);}
	public double getBalance(String name) {return getBalance(getPlayer(name));}
	
	public boolean has(OfflinePlayer op, double amount) {
		double bal = getBalance(op);
		boolean has = bal >= amount;
		return has;
	}
	public boolean has(OfflinePlayer op, String world, double amount) {return has(op, amount);}
	public boolean has(String name, String world, double amount) {return has(name, amount);}
	public boolean has(String name, double amount) {return has(getPlayer(name), amount);}
	
	public EconomyResponse withdrawPlayer(OfflinePlayer op, double amount) {
		ConfigDatabase.withdraw(op, amount);
		double bal = getBalance(op);
		EconomyResponse er = new EconomyResponse(amount, bal, ResponseType.SUCCESS, null);
		return er;
	}
	public EconomyResponse withdrawPlayer(OfflinePlayer op, String world, double amount) {return withdrawPlayer(op, amount);}
	public EconomyResponse withdrawPlayer(String name, String world, double amount) {return withdrawPlayer(name, amount);}
	public EconomyResponse withdrawPlayer(String name, double amount) {return withdrawPlayer(getPlayer(name), amount);}
	
	public EconomyResponse depositPlayer(OfflinePlayer op, double amount) {
		ConfigDatabase.withdraw(op, amount);
		double bal = getBalance(op);
		EconomyResponse er = new EconomyResponse(amount, bal, ResponseType.SUCCESS, null);
		return er;
	}
	public EconomyResponse depositPlayer(OfflinePlayer op, String world, double amount) {return depositPlayer(op, amount);}
	public EconomyResponse depositPlayer(String name, String world, double amount) {return depositPlayer(name, amount);}
	public EconomyResponse depositPlayer(String name, double amount) {return depositPlayer(getPlayer(name), amount);}
	
	public EconomyResponse createBank(String name, String player) {return null;}
	public EconomyResponse createBank(String name, OfflinePlayer op) {return null;}
	public EconomyResponse deleteBank(String name) {return null;}
	public EconomyResponse bankBalance(String name) {return null;}
	public EconomyResponse bankHas(String name, double amount) {return null;}
	public EconomyResponse bankWithdraw(String name, double amount) {return null;}
	public EconomyResponse bankDeposit(String name, double amount) {return null;}
	public EconomyResponse isBankOwner(String name, OfflinePlayer op) {return null;}
	public EconomyResponse isBankOwner(String name, String op) {return null;}
	public EconomyResponse isBankMember(String name, OfflinePlayer op) {return null;}
	public EconomyResponse isBankMember(String name, String op) {return null;}
	public List<String> getBanks() {return null;}
	
	public boolean createPlayerAccount(OfflinePlayer op) {
		ConfigDatabase.load(op);
		return hasAccount(op);
	}
	public boolean createPlayerAccount(OfflinePlayer op, String world) {return createPlayerAccount(op);}
	public boolean createPlayerAccount(String name, String world) {return createPlayerAccount(name);}
	public boolean createPlayerAccount(String name) {return createPlayerAccount(getPlayer(name));}
}