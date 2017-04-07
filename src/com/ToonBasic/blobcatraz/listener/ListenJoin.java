package com.ToonBasic.blobcatraz.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static com.ToonBasic.blobcatraz.command.player.CommandStaff.staff;
import static com.ToonBasic.blobcatraz.utility.Util.prefix;

public class ListenJoin implements Listener {

    public void onStaffJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if (p.hasPermission("blobcatraz.staff.check")) {
            staff.add(uuid);
        }
    }
}
