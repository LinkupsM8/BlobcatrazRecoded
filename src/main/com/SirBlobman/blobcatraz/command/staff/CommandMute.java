package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandMute extends ICommand {
	private static List<Player> mute = Util.newList();
	public CommandMute() {super("mute", "<player>", "blobcatraz.staff.mute", "shutup", "bequiet", "silence");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String target = args[0];
		Player t = Bukkit.getPlayer(target);
		if(t != null) {
			if(mute.contains(t)) {
				mute.remove(t);
				String msg1 = Util.color(prefix + "You are no longer muted!");
				String msg2 = Util.format(prefix + "You unmuted &c%1s", t.getName());
				t.sendMessage(msg1);
				cs.sendMessage(msg2);
			} else {
				mute.add(t);
				String msg1 = Util.color(prefix + "You are no longer allowed to speak.");
				String msg2 = Util.format(prefix + "You muted &c%1s", t.getName());
				t.sendMessage(msg1);
				cs.sendMessage(msg2);
			}
		} else {
			String error = Util.format(prefix + Language.INVALID_TARGET, target);
			cs.sendMessage(error);
		}
	}
}