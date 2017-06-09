package com.ToonBasic.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandMute extends ICommand implements Listener {
    private static List<Player> muted = Util.newList();
    public CommandMute() {super("mute", "<player>", "blobcatraz.staff.mute", "shutup", "bequiet");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length > 0) {
            Player t = Bukkit.getPlayer(args[0]);
            if(t == null) {
            	String error = prefix + Language.INVALID_TARGET;
            	cs.sendMessage(error);
            } else {
                if(muted.contains(t)) {
                    muted.remove(t);
                    t.sendMessage(Util.color(prefix + "You are no longer muted!"));
                    cs.sendMessage(Util.color(prefix + "You have unmuted c" + t.getName()));
                } else {
                    muted.add(t);
                    t.sendMessage(Util.color(prefix + "You have been muted!"));
                    cs.sendMessage(Util.color(prefix + "You have muted &c" + t.getName()));
                }
            }
        }
    }
    
    @EventHandler(priority=EventPriority.HIGHEST)
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if(muted.contains(p)) {
            e.setMessage("");
            e.setCancelled(true);
            p.sendMessage(prefix + "You are currently muted from the chat!");
        }
    }
}
