package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Plugin created by Lilith
 * Plugin redistribution is not permitted without proper authorization
 * You shall not claim any of my open sourced plugins as your own even if you modify the code to your liking
 * Made on 1/28/2017
 */
@ICommand.PlayerOnly
public class CommandFreeze extends ICommand implements Listener {
    private static ArrayList<UUID> freeze = new ArrayList<UUID>();
    public CommandFreeze() {super("freeze", "<name>", "blobcatraz.staff.freeze");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            UUID uuid = target.getUniqueId();
            freeze.add(uuid);
            target.sendMessage(prefix + "You have been frozen! You will not be able to move!");
            cs.sendMessage(prefix + "You have frozen " + target.getName());
        }
    }
    @EventHandler
    public void onPlayerFreeze(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (freeze.contains(p)) {
            e.setCancelled(true);
            p.sendMessage(prefix + "You are frozen! You cannot move!");
        }
    }
}
