package com.SirBlobman.blobcatraz.hub;

import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public class BlockVersion implements Listener {
    public static final List<String> VERSIONS = Util.newList("1.12", "1.11.2", "1.10", "1.9.4");
    @EventHandler(priority=EventPriority.HIGHEST)
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        ProtocolVersion pv = ProtocolSupportAPI.getProtocolVersion(p);
        String version = pv.getName();
        if(!VERSIONS.contains(version)) {
            String error = "The minecraft version '" + version + "' is outdated! Please use 1.12";
            p.kickPlayer(error);
        }
    }
}