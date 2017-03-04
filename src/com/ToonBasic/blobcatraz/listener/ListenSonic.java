package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenSonic implements Listener {
	public static final ItemStack sonic() {
		ItemStack sonic = new ItemStack(Material.BLAZE_ROD, 1);
		ItemMeta meta = sonic.getItemMeta();
		meta.setDisplayName(Util.color("&fSonic &fScrewdriver"));
		sonic.setItemMeta(meta);
		return sonic;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void sonic(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			BlockState bs = b.getState();
			MaterialData md = bs.getData();
			Material mat = md.getItemType();
			ItemStack is = e.getItem();
			if(is != null) {
				ItemStack sonic = sonic();
				if(is.equals(sonic)) {
					e.setCancelled(true);
					PlayerUtil.sonic(p);
					if(md instanceof Openable) {
						Openable o = (Openable) md;
						o.setOpen(!o.isOpen());
						bs.setData((MaterialData) o);
						bs.update();
					} else if(mat == Material.WEB) {
						b.breakNaturally();
					} else if(mat == Material.TNT) {
						World w = b.getWorld();
						Location l = b.getLocation();
						b.setType(Material.AIR);
						TNTPrimed tnt = (TNTPrimed) w.spawnEntity(l, EntityType.PRIMED_TNT);
						tnt.setIsIncendiary(true);
						tnt.setFuseTicks(1000);
						tnt.setYield(50.0F);
						for(Entity ent : tnt.getNearbyEntities(10, 10, 10)) {
							String msg = "As the Doctor would say, RUN!!";
							ent.sendMessage(msg);
						}
					}
				}
			}
		}
	}
}