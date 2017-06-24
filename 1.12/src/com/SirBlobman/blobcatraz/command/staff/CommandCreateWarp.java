package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.config.ConfigWarps;
import com.SirBlobman.blobcatraz.utility.ItemUtil;
import com.SirBlobman.blobcatraz.utility.PlayerUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandCreateWarp extends PlayerCommand {
	public CommandCreateWarp() {super("createwarp", "<warp>", "blobcatraz.staff.createwarp", "setwarp", "addwarp", "makewarp");}
	
	@Override
	public void run(Player p, String[] args) {
		String name = Util.finalArgs(0, args);
		Location l = p.getLocation();
		ItemStack is = PlayerUtil.held(p);
		if(ItemUtil.air(is)) {is = ItemUtil.newItem(Material.ENDER_PEARL);}
		ConfigWarps.save(name, l, is);
		String str = Util.str(l);
		String msg = Util.format(prefix + "You set a warp called &2%1s&r to &a%2s", name, str);
		p.sendMessage(msg);
	}
}