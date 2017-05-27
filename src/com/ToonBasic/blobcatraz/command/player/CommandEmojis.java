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
	private List<String> labelEmojis = Util.newList(":)", "<3", "^", "->", "<-", ":(");
	private List<String> inputEmojis = Util.newList(":\\)", "<3", "\\^", "->", "<-", ":\\(");
	private List<String> outputEmojis = Util.newList("\u263b", "\u2665", "\u2191", "\u2192", "\u2190", "\u2639");

	public CommandEmojis() {super("emojis", null, "blobcatraz.staff.emojis");}

	@Override
	public void handleCommand(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		String sb = Util.color("&lEmojis&r:\n");
		StringBuilder msg = new StringBuilder(sb);
		for (int i = 0; i < labelEmojis.size(); i++) {
			String label = labelEmojis.get(i);
			String output = outputEmojis.get(i);
			String app = Util.color(label + " &lis&r " + output + "\n");
			msg.append(app);
		}
		String send = msg.toString();
		p.sendMessage(send);
	}

	@EventHandler
	public void emojis(AsyncPlayerChatEvent e) {
		String msg = e.getMessage();
		for (int i = 0; i < labelEmojis.size(); i++) {
			String input = inputEmojis.get(i);
			String output = outputEmojis.get(i);
			msg = msg.replaceAll(input, output);
		}
		e.setMessage(msg);
	}
}