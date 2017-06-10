package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigWarps;
import com.ToonBasic.blobcatraz.utility.ItemUtil;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSetWarp extends ICommand {
	public CommandSetWarp() {super("setwarp", "<name>", "blobcatraz.staff.setwarp", "createwarp");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		PlayerInventory pi = p.getInventory();
		if(args.length > 0) {
			String name = Util.finalArgs(0, args);
			Location warp = p.getLocation();
			ItemStack icon = pi.getItemInMainHand();
			if(ItemUtil.air(icon)) icon = new ItemStack(Material.ENDER_PEARL);
			ConfigWarps.save(name, warp, icon);
			String str = Util.str(warp);
			String msg = Util.color(prefix + "You set a warp called &2" + name + "&r to " + str);
			p.sendMessage(msg);
		}
	}
}