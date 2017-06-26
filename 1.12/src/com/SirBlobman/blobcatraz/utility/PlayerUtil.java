package com.SirBlobman.blobcatraz.utility;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;

import java.util.Set;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class PlayerUtil extends Util {
	public static void ping(Player p) {
		Location l = p.getLocation();
		Sound s = Sound.ENTITY_EXPERIENCE_ORB_PICKUP;
		float v = 1000.0F;
		float pi = 1.0F;
		p.playSound(l, s, v, pi);
	}
	
	public static String getPing(Player p) {
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		int ping = ep.ping;
		String ret = Integer.toString(ping);
		if(ping < 0) ret = Integer.toString(-1);
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
		Vector v = l.getDirection();
		Vector n = v.normalize();
		for(int i = 1; i <= 200; i++) {
			l.add(n);
			Block b = l.getBlock();
			Material mat = b.getType();
			if(mat != Material.AIR) {return l;}
		}
		
		Block b = lookBlock(p);
		Location r = b.getLocation();
		return r;
	}
	
	public static void action(Player p, String msg) {
		String c = color(msg);
		String json = toJSON(c);
		
		IChatBaseComponent icbc = ChatSerializer.a(json);
		ChatMessageType ACTION = ChatMessageType.GAME_INFO;
		PacketPlayOutChat ppoc = new PacketPlayOutChat(icbc, ACTION);
		
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		PlayerConnection pc = ep.playerConnection;
		pc.sendPacket(ppoc);
	}
	
	public static boolean within(Player p, Location l1, Location l2) {
		if(p == null || l1 == null || l2 == null) return false;
		World pw = p.getWorld(), w1 = l1.getWorld(), w2 = l2.getWorld();
		String np = pw.getName(), n1 = w1.getName(), n2 = w2.getName();
		if(np.equals(n1) && n1.equals(n2)) {	
			int x1 = l1.getBlockX(), x2 = l2.getBlockX(),
			y1 = l1.getBlockY(), y2 = l2.getBlockY(),
			z1 = l1.getBlockZ(), z2 = l2.getBlockZ();
			
			int topX = Math.max(x1, x2), botX = Math.min(x1, x2),
			topY = Math.max(y1, y2), botY = Math.min(y1, y2),
			topZ = Math.max(z1, z2), botZ = Math.min(z1, z2);
			
			Location pl = p.getLocation();
			int px = pl.getBlockX(), py = pl.getBlockY(), pz = pl.getBlockZ();

			boolean x = ((px <= topX) && (px >= botX));
			boolean y = ((py <= topY) && (py >= botY));
			boolean z = ((pz <= topZ) && (pz >= botZ));
			boolean b = (x && y && z);
			return b;
		} else return false;
	}
	
	public static TextComponent death(Player p, String msg) {
		String name = p.getName();
		String text = color(name + "&f was revived");
		TextComponent txt = new TextComponent(text);
		ComponentBuilder cb = new ComponentBuilder(msg);
		BaseComponent[] bc = cb.create();
		HoverEvent he = new HoverEvent(Action.SHOW_TEXT, bc);
		txt.setHoverEvent(he);
		return txt;
	}
	
	public static ItemStack held(Player p) {
		PlayerInventory pi = p.getInventory();
		ItemStack is = pi.getItemInMainHand();
		return is;
	}
}