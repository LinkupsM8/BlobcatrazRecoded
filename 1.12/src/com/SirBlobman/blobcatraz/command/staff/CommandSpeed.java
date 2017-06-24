package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSpeed extends PlayerCommand {
	public CommandSpeed() {super("speed", "<player> <sped/reset> [walk/fly]", "blobcatraz.staff.speed");}
	
	@Override
	public void run(Player p, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			String name = t.getName();
			String perm = "blobcatraz.staff.speed.others";
			if(!p.equals(t) && !p.hasPermission(perm)) {
				String error = Util.format(prefix + Language.NO_PERMISSION, perm);
				p.sendMessage(error);
				return;
			}
			
			String sub = args[1].toLowerCase();
			float speed = 0.2F;
			if(sub.equals("reset")) {
				t.setWalkSpeed(speed);
				t.setFlySpeed(speed);
				String msg1 = prefix + "Your speed was reset!";
				String msg2 = prefix + "You reset the speed of " + name;
				t.sendMessage(msg1);
				p.sendMessage(msg2);
			} else {
				int i = NumberUtil.getInteger(sub);
				if(i < -100 || i > 100) {
					String error = Util.color(prefix + "&cThe speed must be an integer between -100 and 100");
					p.sendMessage(error);
				} else {
					speed = ((float) i / 100.0F);
					if(args.length > 2) {
						String sub2 = args[2].toLowerCase();
						if(sub2.equals("walk")) {
							t.setWalkSpeed(speed);
							String msg1 = Util.format(prefix + "Your walk speed was set to &b%1s&f!", i);
							String msg2 = Util.format(prefix + "You set the walk speed of &a%1s&f to &b%2s&f!", name, i);
							t.sendMessage(msg1);
							p.sendMessage(msg2);
						} else if(sub2.equals("fly")) {
							t.setFlySpeed(speed);
							String msg1 = Util.format(prefix + "Your fly speed was set to &b%1s&f!", i);
							String msg2 = Util.format(prefix + "You set the fly speed of &a%1s&f to &b%2s&f!", name, i);
							t.sendMessage(msg1);
							p.sendMessage(msg2);
						} else {
							String error = getFormattedUsage(getCommandUsed());
							p.sendMessage(error);
						}
					} else {
						t.setWalkSpeed(speed);
						t.setFlySpeed(speed);
						String msg1 = Util.format(prefix + "Your walk/fly speed was set to &b%1s&f!", i);
						String msg2 = Util.format(prefix + "You set the walk/fly speed of &a%1s&f to &b%2s&f!", name, i);
						t.sendMessage(msg1);
						p.sendMessage(msg2);
					}
				}
			}
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			p.sendMessage(error);
		}
	}
}