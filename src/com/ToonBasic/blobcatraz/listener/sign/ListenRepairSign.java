package com.ToonBasic.blobcatraz.listener.sign;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.config.ConfigDatabase;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenRepairSign implements Listener {
	private static final String REPAIR = Util.color("&b&l[Repair]");
	
	@EventHandler
	public void sign(SignChangeEvent e) {
		Player p = e.getPlayer();
		if(p.hasPermission("blobcatraz.sign.repair")) {
			String l0 = e.getLine(0).toLowerCase();
			if(l0.equals("[repair]")) {
				e.setLine(0, REPAIR);
				e.setLine(1, Util.money(100000.00D));
				String msg = "Created repair sign!";
				PlayerUtil.action(p, msg);
			}
		}
	}
	
	@EventHandler
	public void click(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			if(bs instanceof Sign) {
				Sign s = (Sign) bs;
				String l0 = s.getLine(0);
				if(l0.equals(REPAIR)) {
					ItemStack is = e.getItem();
					if(is != null) {
						double price = 100000.00D;
						double bal = ConfigDatabase.balance(p);
						if(bal >= price) {
							String name = ItemUtil.name(is);
							ConfigDatabase.withdraw(p, price);
							is.setDurability(Util.toShort(0));
							PlayerUtil.action(p, "Successfully repaired your &a" + name);
						} else {
							String error = "You don't have enough money!";
							PlayerUtil.action(p, error);
						}
					} else {
						String error = "You cannot repair AIR";
						PlayerUtil.action(p, error);
					}
				}
			}
		}
	}
}