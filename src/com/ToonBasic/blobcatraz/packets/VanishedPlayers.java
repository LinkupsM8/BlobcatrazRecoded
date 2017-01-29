package com.ToonBasic.blobcatraz.packets;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class VanishedPlayers {
	private final ProtocolManager pm = ProtocolLibrary.getProtocolManager();
	public static Set<UUID> vanished = Sets.newHashSet();

	public VanishedPlayers(Plugin plugin) {
		pm.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.SPAWN_ENTITY) { //Listen to when a player may view a possible entity
			@Override
			public void onPacketSending(PacketEvent event) {
				Entity entity = event.getPacket().getEntityModifier(event).read(0);
				if (entity != null
						&& entity instanceof Player
						&& vanished.contains(entity.getUniqueId())) { //Player can potentially see the vanished player
					showAsVanished(event.getPlayer(), (Player) entity); //This will show the vanished player semi-transparent
				}
			}
		});
	}

	public void addVanished(Player player) {
		if (vanished.add(player.getUniqueId())) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 2), true); //Apply invisibility to the vanished user
			showAsVanished(player, player); //Let people who are vanished view themselves transparently
			for (Player viewer : pm.getEntityTrackers(player)) { //Send a packet to anyone who can see the vanished player transparently
				showAsVanished(viewer, player);
			}
		}
	}

	public void removeVanished(Player player) {
		if (vanished.remove(player.getUniqueId())) {
			player.removePotionEffect(PotionEffectType.INVISIBILITY); //Remove invisibility
			for (Player viewer : Bukkit.getServer().getOnlinePlayers()) { //Send removal packets to every player
				PacketContainer packet = pm.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM, true);
				packet.getStrings().write(0, viewer.getEntityId() + "." + player.getEntityId()); //Make the team name unique to both the viewer and the ghost
				packet.getIntegers().write(1, 1); //We are removing this from viewing
				try {
					pm.sendServerPacket(viewer, packet); //Only the viewer needs to be sent the packet
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void showAsVanished(Player viewer, Player player) {
		PacketContainer packet = pm.createPacket(PacketType.Play.Server.SCOREBOARD_TEAM, true);
		packet.getStrings().write(0, viewer.getEntityId() + "." + player.getEntityId()); //Make the team name unique to both the viewer and the ghost
		packet.getIntegers().write(1, 0); //We are creating a new team
		packet.getModifier().write(6, Lists.newArrayList(viewer.getName(), player.getName())); //Contains Vanished Player and Viewers (People who can view them)
		packet.getIntegers().write(2, 3); //Ghost can be seen and attacked by the viewer
		try {
			pm.sendServerPacket(viewer, packet); //Only the viewer needs to be sent the packet
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}