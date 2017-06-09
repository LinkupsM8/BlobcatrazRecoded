package com.ToonBasic.blobcatraz.command.player;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;
import com.ToonBasic.blobcatraz.utility.Util;

@PlayerOnly
public class CommandEmojis extends ICommand implements Listener {	
	private static final List<String> label = Util.newList(":)", "<3", "^", "->", "<-", ":(");
	private static final List<String> input = Util.newList(":\\)", "<3", "\\^", "->", "<-", ":\\(");
	private static final List<String> output = Util.newList("\u263b", "\u2764", "\u2191", "\u2192", "\u2190", "\u2639");

	public CommandEmojis() {super("emojis", null, "blobcatraz.staff.emojis");}

	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		Player p = (Player) cs;
		String sb = Util.color("&lEmojis&r:\n");
		StringBuilder msg = new StringBuilder(sb);
		for (int i = 0; i < label.size(); i++) {
			String la = label.get(i);
			String ou = output.get(i);
			String app = Util.color(la + " &lis&r " + ou + "\n");
			msg.append(app);
		}
		String send = msg.toString();
		p.sendMessage(send);
	}

	@EventHandler
	public void emojis(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		msg = format(msg);
		e.setMessage(msg);
	}
	
	public static String format(String o) {
		String c = o;
		for(int i = 0; i < label.size(); i++) {
			String in = input.get(i);
			String out = output.get(i);
			c = c.replaceAll(in, out);
		}
		return c;
	}
}