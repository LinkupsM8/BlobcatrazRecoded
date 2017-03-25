package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.UUID;

@ICommand.PlayerOnly
public class CommandGod extends ICommand implements Listener {

    private static List<UUID> god = Util.newList();

    public CommandGod() {super("god", "", "blobcatraz.staff.god", "jesus", "toonrocks");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        UUID uuid = p.getUniqueId();
        if(god.contains(uuid)) {
            god.remove(uuid);
            p.sendMessage(prefix + "You are no longer in god mode!");
        } else {
            god.add(uuid);
            p.sendMessage(prefix + "You are now in god mode!");
        }
    }
    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            UUID peuuid = p.getUniqueId(); //Get player's UUID through event
            if(god.contains(peuuid)) {
                e.setCancelled(true);
            }
        }
    }
}
