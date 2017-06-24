package com.SirBlobman.blobcatraz.command.special;

import static org.bukkit.Material.AIR;
import static org.bukkit.Material.FIRE;
import static org.bukkit.Material.GLASS;
import static org.bukkit.Material.LADDER;
import static org.bukkit.Material.OBSIDIAN;
import static org.bukkit.Material.STAINED_GLASS;
import static org.bukkit.Material.STAINED_GLASS_PANE;
import static org.bukkit.Material.THIN_GLASS;
import static org.bukkit.Material.TNT;
import static org.bukkit.Material.VINE;
import static org.bukkit.Material.WEB;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.material.Door;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSonic extends PlayerCommand implements Listener {
	private static final ItemStack SONIC = ItemUtil.newItem(Material.BLAZE_ROD, 1, 0, "&fSonic &fScrewdriver");
	private static final List<Material> BREAK_LIST = Util.newList(WEB, LADDER, VINE);
	private static final List<Material> GLASS_LIST = Util.newList(GLASS, THIN_GLASS, STAINED_GLASS, STAINED_GLASS_PANE);
	public CommandSonic() {super("sonic", "", "blobcatraz.special.sonic", "screwdriver", "doctorwho", "sonicscrewdriver");}
	
	@Override
	public void run(Player p, String[] args) {
		PlayerInventory pi = p.getInventory();
		pi.addItem(SONIC);
		p.sendMessage("Try to find the TARDIS now.");
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void sonic(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		ItemStack is = PlayerUtil.held(p);
		if(!ItemUtil.air(is)) {
			if(is.equals(SONIC)) {
				Entity en = e.getRightClicked();
				PlayerUtil.sonic(p);
				if(en instanceof Creeper) {
					Creeper c = (Creeper) en;
					c.setPowered(true);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGHEST)
	public void sonic(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_BLOCK) {
			ItemStack is = e.getItem();
			if(!ItemUtil.air(is)) {
				if(is.equals(SONIC)) {
					e.setCancelled(true);
					Block b = e.getClickedBlock();
					Block u = b.getRelative(BlockFace.UP);
					Block d = b.getRelative(BlockFace.DOWN);
					
					Material bmat = b.getType();
					Material umat = u.getType();
					BlockState bs = b.getState();
					MaterialData md = bs.getData();
					
					PlayerUtil.sonic(p);
					if(md instanceof Openable) {
						if(md instanceof Door) {
							Door door = (Door) md;
							if(door.isTopHalf()) {
								bs = d.getState();
								md = bs.getData();
							}
						}
						
						Openable o = (Openable) md;
						o.setOpen(!o.isOpen());
						bs.setData((MaterialData) o);
						bs.update();
					} else if(bmat == TNT) {
						World w = b.getWorld();
						Location l = b.getLocation();
						b.setType(AIR);
						TNTPrimed tnt = w.spawn(l, TNTPrimed.class);
						tnt.setIsIncendiary(true);
						tnt.setFuseTicks(500);
						tnt.setYield(100.0F);
						for(Entity en : tnt.getNearbyEntities(20, 20, 20)) {
							String msg = "As the Doctor would say, \"RUN!!\"";
							en.sendMessage(msg);
						}
					} else if(bmat == OBSIDIAN && umat == AIR) u.setType(FIRE);
					else if(BREAK_LIST.contains(bmat)) b.breakNaturally();
					else if(GLASS_LIST.contains(bmat)) {
						byte data = md.getData();
						ItemStack glass = ItemUtil.newItem(bmat, 1, data);
						b.breakNaturally();
						p.getInventory().addItem(glass);
					}
				}
			}
		}
	}
}