package com.SirBlobman.blobcatraz.listener.sign;

import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ListenSign implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void click(PlayerInteractEvent e) {
		if(e.isCancelled()) return;
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			if(bs instanceof Sign) {
				Sign s = (Sign) bs;
				SignClickEvent sce = new SignClickEvent(p, b, s);
				Util.callEvent(sce);
			}
		}
	}
}