package com.ToonBasic.blobcatraz.command.staff;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandPowertool extends ICommand implements Listener {
	private static Map<UUID, Map<Material, String>> tools = Util.newMap();
	public CommandPowertool() {super("powertool", "<command>", "blobcatraz.staff.powertool", "pt", "bind");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		UUID uuid = p.getUniqueId();
		if(args[0].equals("-")) {
			tools.put(uuid, Util.newMap());
			p.sendMessage("Your powertools were cleared");
		} else {
			PlayerInventory pi = p.getInventory();
			ItemStack is = pi.getItemInMainHand();
			if(is != null && is.getType() != Material.AIR) {
				Material mat = is.getType();
				if(!mat.isBlock()) {
					String cmd = Util.finalArgs(0, args);
					Map<Material, String> ptools = Util.newMap();
					if(tools.containsKey(uuid)) {
						ptools = tools.get(uuid);
					}
					ptools.put(mat, cmd);
					tools.put(uuid, ptools);
					p.sendMessage(prefix + Util.color("Your &b" + mat.name() + "&f is now binded to the command &a/" + cmd));
				} else {
					String error = "You cannot use a block as a powertool";
					p.sendMessage(error);
				}
			} else {
				String error = "You can't have a powertool on AIR";
				p.sendMessage(error);
			}
		}
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		if(tools.containsKey(uuid)) {
			Map<Material, String> ptools = tools.get(uuid);
			ItemStack is = e.getItem();
			if(is != null && ptools.containsKey(is.getType())) {
				Material mat = is.getType();
				String cmd = ptools.get(mat);
				e.setCancelled(true);
				p.performCommand(cmd);
			}
		}
	}
}