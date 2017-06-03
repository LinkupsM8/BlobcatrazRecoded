package com.SirBlobman.blobcatraz.shop;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;

import com.SirBlobman.blobcatraz.shop.command.CommandShopNPC;
import com.SirBlobman.blobcatraz.shop.compat.citizens.ShopTrait;
import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.command.CommandFramework;
import com.ToonBasic.blobcatraz.utility.Util;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitFactory;
import net.citizensnpcs.api.trait.TraitInfo;

public class ShopCore extends Core {
	private static final Server SERVER = Bukkit.getServer();
	private static final PluginManager PM = SERVER.getPluginManager();
	public static ShopCore instance;
	public CommandFramework framework;
	
	@Override
	public void onEnable() {
		if(PM.isPluginEnabled("Citizens")) {
			instance = this;
			framework = new CommandFramework(this);
			framework.registerCommands(new CommandShopNPC());
			framework.registerCommands();
			
			TraitFactory tf = CitizensAPI.getTraitFactory();
			TraitInfo ti = TraitInfo.create(ShopTrait.class);
			tf.registerTrait(ti);
			Util.regEvents(this, new ShopTrait());
			Util.print("Shop addon enabled!");
		} else {
			String error = "Citizens v2.0 is required!";
			Util.print(error);
			PM.disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		Util.print("Shop addon disabled!");
	}
}