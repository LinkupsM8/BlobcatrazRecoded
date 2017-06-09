package com.ToonBasic.blobcatraz.command.staff;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

@ICommand.PlayerOnly
public class CommandFreeze extends ICommand implements Listener {
    private static List<UUID> freeze = Util.newList();
    public CommandFreeze() {super("freeze", "<name>", "blobcatraz.staff.freeze");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            UUID uuid = target.getUniqueId();
            if (freeze.contains(uuid)) {
                freeze.remove(uuid);
                target.sendMessage(prefix + "You are no longer frozen! Be free!");
                cs.sendMessage(prefix + "You have unfrozen " + target.getName());
            } else {
                freeze.add(uuid);
                target.sendMessage(prefix + "You have been frozen! You will not be able to move!");
                cs.sendMessage(prefix + "You have froze " + target.getName());
            }
        }
    }
    @EventHandler
    private void onPlayerFreeze(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if (freeze.contains(uuid)) {
            if (hasChangedBlockCoordinates(e.getFrom(), e.getTo())) {
                Location from = e.getFrom();
                double x = Math.floor(from.getX());
                double z = Math.floor(from.getZ());

                x += .5;
                z += .5;
                p.teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
                p.sendMessage(prefix + "You are frozen! You cannot move!");
            }
        }
    }
    private boolean hasChangedBlockCoordinates(final Location fromLoc, final Location toLoc) {
        return !(fromLoc.getWorld().equals(toLoc.getWorld())
                && fromLoc.getBlockX() == toLoc.getBlockX()
                && fromLoc.getBlockY() == toLoc.getBlockY()
                && fromLoc.getBlockZ() == toLoc.getBlockZ());
    }
}