package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.UUID;

public class CommandMute extends ICommand implements Listener {

    private static List<UUID> muted = Util.newList();


    public CommandMute() {
        super("mute", "<player>", "blobcatraz.player.mute", "shutup", "bequiet");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length == 0) {
            Player t = Bukkit.getPlayer(args[0]);
            UUID uuid = t.getUniqueId();
            if(muted.contains(uuid)) {
                muted.remove(uuid);
                t.sendMessage(prefix + "You are no longer muted!");
                cs.sendMessage(prefix + "You have unmuted &c" + t.getName());
            } else {
                muted.add(uuid);
                t.sendMessage(prefix + "You have been muted!");
                cs.sendMessage(prefix + "You have muted &c" + t.getName());
            }
        }
    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if(muted.contains(uuid)) {
            e.setCancelled(true);
            p.sendMessage(prefix + "You are currently muted from the chat!");
        }
    }
}
