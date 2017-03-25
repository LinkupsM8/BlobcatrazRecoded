package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.config.ConfigWarps;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandSetWarp extends ICommand {
	public CommandSetWarp() {super("setwarp", "<name>", "blobcatraz.staff.setwarp", "createwarp");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		if(args.length > 0) {
			String name = Util.finalArgs(0, args);
			Location warp = p.getLocation();
			ItemStack icon = new ItemStack(Material.ENDER_PORTAL_FRAME);
			ConfigWarps.save(name, warp, icon);
			p.sendMessage(Util.color("You have set warp &2" + name + " &rto your current location"));
		}
	}
}