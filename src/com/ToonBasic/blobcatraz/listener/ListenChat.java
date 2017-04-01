package com.ToonBasic.blobcatraz.listener;

import static com.ToonBasic.blobcatraz.command.staff.CommandChat.isChatMuted;
import static com.ToonBasic.blobcatraz.utility.Util.prefix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ToonBasic.blobcatraz.utility.PlayerUtil;
import com.ToonBasic.blobcatraz.utility.Util;

public class ListenChat implements Listener {
	@EventHandler(priority = EventPriority.HIGH)
	public void chat(AsyncPlayerChatEvent e) {
		String format = Util.color("&f%1s &7>>&f %2s");
		e.setFormat(format);

		Player p = e.getPlayer();
		String msg = e.getMessage();
		if (p.hasPermission("blobcatraz.chat.color")) {
			String cmsg = Util.color(msg);
			e.setMessage(cmsg);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void ping(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String pname = p.getName();
		String msg = e.getMessage();
		for (Player on : Bukkit.getOnlinePlayers()) {
			String oname = on.getName();
			if (!oname.equals(pname)) {
				String lmsg = msg.toLowerCase();
				String loname = oname.toLowerCase();
				if (lmsg.contains(loname)) {
					PlayerUtil.ping(on);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (isChatMuted) {
			if (p.isOp() || p.hasPermission("blobcatraz.chat.bypass")) {
				e.setCancelled(false);
			} else {
				e.setCancelled(true);
				p.sendMessage(prefix + "The chat is currently muted!");
			}
		}
	}

	@EventHandler
	public void onPlayerSwear(AsyncPlayerChatEvent e) {
		//List of bad words
		String[] swearWords = {"fuck", "bitch", "cunt", "whore", "slut", "nigger", "nigga", "shit"};
		//End of list
		String message = e.getMessage().toLowerCase().replaceAll("@", "a").replaceAll("\\p{Punct}", " ");
		for (String word : swearWords) {
			if (message.matches("(. )?" + word + "( .)?")) {
				//Swear word has been found and said
				e.getPlayer().sendMessage(prefix + "Please refrain from swearing! You are not in trouble, but we wish to maintain a clean enviroment :).");
			} else {
				//Do nothing
			}
		}
	}

	@EventHandler
    public void onStaffChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("blobcatraz.staff.chat")) {
            if (e.getMessage().startsWith("@")) {
                e.setCancelled(true);

                String message = e.getMessage().substring(1);
                for (Player staffChat : Bukkit.getOnlinePlayers()) {
                    if (staffChat.hasPermission("blobcatraz.staff.chat")) {
                        staffChat.sendMessage(Util.color("&c[SC] &a" + p.getName() + " &8>&f " + message));
                    }
                }
            } else {
                e.setCancelled(false);
            }
        }
    }
}