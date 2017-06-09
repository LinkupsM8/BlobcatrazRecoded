package com.ToonBasic.blobcatraz.utility;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.PlayerConnection;

public class PlayerUtil extends Util {
	public static void ping(Player p) {
		Location l = p.getLocation();
		Sound s = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		float v = 0.1F;
		float pi = 1.0F;
		p.playSound(l, s, v, pi);
	}
	
	public static String getPing(Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		int ping = ep.ping;
		String ret = Integer.toString(ping);
		if(ping < 0) ret = "" + -1;
		if(ping > 1000) ret = "1000+";
		return ret;
	}
	
	public static void sonic(Player p) {
		Location l = p.getLocation();
		String s = "tool.sonic.screwdriver";
		float v = 0.1F;
		float pi = 0.1F;
		p.playSound(l, s, v, pi);
	}
	
	public static Block lookBlock(Player p) {
		Set<Material> set = null;
		Block b = p.getTargetBlock(set, 200);
		return b;
	}
	
	public static Location lookLocation(Player p) {
		Location l = p.getEyeLocation();
		Vector v = l.getDirection().normalize();
		for(int i = 1; i <= 200; i++) {
			l.add(v);
			Block b = l.getBlock();
			Material mat = b.getType();
			if(mat != Material.AIR) {return l;}
		}
		return lookBlock(p).getLocation();
	}
	
	public static void action(Player p, String msg) {
		msg = color(msg);
		String json = json(msg);
		IChatBaseComponent icbc = ChatSerializer.a(json);
		ChatMessageType cmt = ChatMessageType.GAME_INFO;
		PacketPlayOutChat ppoc = new PacketPlayOutChat(icbc, cmt);
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		PlayerConnection pc = ep.playerConnection;
		pc.sendPacket(ppoc);
	}
	
	public static boolean within(Player p, Location l1, Location l2) {
		int x1 = l1.getBlockX(), x2 = l2.getBlockX(),
		y1 = l1.getBlockY(), y2 = l2.getBlockY(),
		z1 = l1.getBlockZ(), z2 = l2.getBlockZ();
		
		int ax = Math.max(x1, x2), bx = Math.min(x1, x2),
		ay = Math.max(y1, y2), by = Math.min(y1, y2),
		az = Math.max(z1, z2), bz = Math.min(z1, z2);
		
		Location pl = p.getLocation();
		int px = pl.getBlockX(), py = pl.getBlockY(), pz = pl.getBlockZ();

		boolean x = ((px <= ax) && (px >= bx));
		boolean y = ((py <= ay) && (py >= by));
		boolean z = ((pz <= az) && (pz >= bz));
		return (x && y && z);
	}
    
    public static TextComponent death(Player p, String msg) {
    	String name = p.getName();
    	TextComponent text = new TextComponent(color(name + " &fwas successfully revived by our medics"));
    	BaseComponent[] bc = new ComponentBuilder(msg).create();
    	HoverEvent he = new HoverEvent(Action.SHOW_TEXT, bc);
    	text.setHoverEvent(he);
    	return text;
    }
	
	public static ItemStack held(Player p) {
		PlayerInventory pi = p.getInventory();
		ItemStack held = pi.getItemInMainHand();
		return held;
	}
}