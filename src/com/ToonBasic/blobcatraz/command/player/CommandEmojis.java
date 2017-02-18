package com.ToonBasic.blobcatraz.command.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandEmojis extends ICommand implements Listener {
	
		private List<String> labelEmojis = new ArrayList<String>(Arrays.asList(":)", "<3", "^", "->", "<-"));
		private List<String> inputEmojis = new ArrayList<String>(Arrays.asList(":\\)", "<3", "\\^", "->", "<-"));
		private List<String> outputEmojis = new ArrayList<String>(Arrays.asList("☻", "♥", "↑", "→", "←"));

		public CommandEmojis() {
			
			super("emojis", null, "blobcatraz.staff.emojis");
			
		}

		@Override
		public void handleCommand(CommandSender sender, String[] args) {
			
			Player p = (Player) sender;
			
			StringBuilder msg = new StringBuilder("§lEmojis§r:\n");
			
			for (int i = 0; i < labelEmojis.size(); i++) {
				
				msg.append(labelEmojis.get(i) + " §lis§r " + outputEmojis.get(i) + "\n");
				
			}
			
			p.sendMessage(msg.toString()); 
			
		}
		
		@EventHandler
		public void onPlayerChat(AsyncPlayerChatEvent e) {
			
			String msg = e.getMessage();
			
			for (int i = 0; i < labelEmojis.size(); i++) {
				
				msg = msg.replaceAll(inputEmojis.get(i), outputEmojis.get(i));
				
			}
			
			e.setMessage(msg);
			
		}
	
}
