package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandHeal extends ICommand {
	public CommandHeal() {super("heal", "[player]", "blobcatraz.staff.heal");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		Player t = p;
		if(args.length > 0) {
			String target = args[0];
			t = Bukkit.getPlayer(target);
			if(t == null) t = p;
		}
		double max = t.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
		t.setHealth(max);
		t.sendMessage("You were healed!");
		if(t != p) p.sendMessage(Util.color("You healed &5" + t.getName()));
	}
}