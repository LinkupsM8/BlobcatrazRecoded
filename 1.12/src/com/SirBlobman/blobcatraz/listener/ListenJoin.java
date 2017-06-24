package com.SirBlobman.blobcatraz.listener;

import com.SirBlobman.blobcatraz.utility.NumberUtil;
import com.SirBlobman.blobcatraz.utility.Util;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class ListenJoin implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void join(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		String name = p.getName();
		boolean noob = !p.hasPlayedBefore();
		if(noob) {
			p.performCommand("hub");
			String msg1 = Util.format(Util.PREFIX + "&dPlease welcome &f%1s &dto &3&lBlobcatraz&d!", name);
			String msg2 = Util.color("&eDon't forget to do &a/rules");
			Util.broadcast(msg1);
			p.sendMessage(msg2);
		} else {
			String msg = Util.color(Util.PREFIX + "&dWelcome back \u264b");
			p.sendMessage(msg);
		}
		fireworks(p);
	}
	
	private void fireworks(Player p) {
		Location l = p.getLocation();
		World w = l.getWorld();
		Random rand = NumberUtil.random();
		for(int i = 0; i < rand.nextInt(5) + 1; i++) {
			int r = rand.nextInt(4) + 1;
			Type type = Type.BALL;
        	switch(r) {
        	case 1: {type = Type.BALL; break;}
        	case 2: {type = Type.BALL_LARGE; break;}
        	case 3: {type = Type.BURST; break;}
        	case 4: {type = Type.CREEPER; break;}
        	case 5: {type = Type.STAR; break;}
        	default: {type = Type.BALL; break;}
        	}
        	
        	Color c1 = Color.AQUA;
        	Color c2 = Color.BLUE;
        	Builder b = FireworkEffect.builder();
        	b = b.flicker(rand.nextBoolean());
        	b = b.withColor(c1);
        	b = b.withFade(c2);
        	b = b.with(type);
        	b = b.trail(rand.nextBoolean());
        	FireworkEffect fe = b.build();
        	
        	Firework fw = w.spawn(l, Firework.class);
        	FireworkMeta fm = fw.getFireworkMeta();
        	int rp = rand.nextInt(2) + 1;
        	fm.addEffect(fe);
        	fm.setPower(rp);
        	fw.setFireworkMeta(fm);
		}
	}
}