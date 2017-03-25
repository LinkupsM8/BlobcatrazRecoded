package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class CommandChat extends ICommand {

    public static List<UUID> chatMuted = Util.newList();

    public CommandChat() {
        super("chat", "[enable/disable]", "blobcatraz.player.chat", "ch");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args[0].equals("disable")) {
            cs.sendMessage(prefix + "You have muted the chat!");
            for (Player p : Bukkit.getOnlinePlayers()) {
                UUID uuid = p.getUniqueId();
                chatMuted.add(uuid);
                Bukkit.broadcastMessage(Util.color(prefix + "The chat has been muted by: &c" + cs.getName()));
            }
        } else if (args[0].equals("enable")) {
            cs.sendMessage(prefix + "You have unmuted the chat!");
            for (Player p : Bukkit.getOnlinePlayers()) {
                UUID uuid = p.getUniqueId();
                chatMuted.remove(uuid);
                Bukkit.broadcastMessage(Util.color(prefix + "The chat has been unmuted by: &c" + cs.getName()));
            }
        } else {
            cs.sendMessage(prefix + "Invalid arguments!");
        }
    }
}
