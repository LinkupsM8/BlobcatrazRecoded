package com.SirBlobman.blobcatraz.command.special;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandPowerTool extends PlayerCommand implements Listener {
	private static Map<Player, Map<Material, String>> tools = Util.newMap();
	public CommandPowerTool() {super("powertool", "<command>", "blobcatraz.special.powertool", "pt", "bind");}
	
	@Override
	public void run(Player p, String[] args) {
		String sub = Util.finalArgs(0, args);
		ItemStack is = PlayerUtil.held(p);
		if(ItemUtil.air(is)) {
			String error = prefix + "AIR is not a tool!";
			p.sendMessage(error);
		} else {
			Material mat = is.getType();
			boolean b = mat.isBlock();
			if(b) {
				String error = prefix + "You cannot use a block as a powertool!";
				p.sendMessage(error);
			} else {
				if(sub.equals("-")) {
					Map<Material, String> map = Util.newMap();
					if(tools.containsKey(p)) map = tools.get(p);
					if(map.containsKey(mat)) {
						map.remove(mat);
						String msg = prefix + "Successfully removed the powertool from that item";
						p.sendMessage(msg);
					} else {
						String error = prefix + "That item is not a powertool";
						p.sendMessage(error);
					}
				} else if(sub.equals("--")) {
					Map<Material, String> map = Util.newMap();
					tools.put(p, map);
					String msg = prefix + "You cleared all your powertools";
					p.sendMessage(msg);
				} else {
					Map<Material, String> map = Util.newMap();
					if(tools.containsKey(p)) map = tools.get(p);
					map.put(mat, sub);
					tools.put(p, map);
					String msg = Util.format(prefix + "Your &b%1s&f is now bound to the command &a/%2s", mat.name(), sub);
					p.sendMessage(msg);
				}
			}
		}
	}
	
	@EventHandler
	public void run(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(tools.containsKey(p)) {
			ItemStack is = e.getItem();
			if(!ItemUtil.air(is)) {
				Map<Material, String> map = tools.get(p);
				Material mat = is.getType();
				if(map.containsKey(mat)) {
					e.setCancelled(true);
					String cmd = map.get(mat);
					p.performCommand(cmd);
				}
			}
		}
	}
}