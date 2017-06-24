package com.SirBlobman.blobcatraz.command.player;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandEmojis extends PlayerCommand implements Listener {
	private static final List<String> LABEL = Util.newList(":)", "<3", "^", "->", "<-", ":(");
	private static final List<String> INPUT = Util.newList(":\\)", "<3", "\\^", "->", "<-", ":\\(");
	private static final List<String> OUTPUT = Util.newList("\u263b", "\u2764", "\u2191", "\u2192", "\u2190", "\u2639");
	public CommandEmojis() {super("emojis", "", "blobcatraz.staff.emojis");}
	
	@Override
	public void run(Player p, String[] args) {
		String sb = Util.color("&lEmojis&r:\n");
		StringBuilder msg = new StringBuilder(sb);
		for(int i = 0; i < LABEL.size(); i++) {
			String la = LABEL.get(i);
			String ou = OUTPUT.get(i);
			String ap = Util.format("%1s &lis&r %2s\n", la, ou);
			msg.append(ap);
		}
		
		String s = msg.toString();
		p.sendMessage(s);
	}
	
	@EventHandler
	public void e(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		String f = format(msg);
		e.setMessage(f);
	}
	
	public static String format(String o) {
		for(int i = 0; i < LABEL.size(); i++) {
			String in = INPUT.get(i);
			String ou = OUTPUT.get(i);
			o = o.replaceAll(in, ou);
		}
		return o;
	}
}