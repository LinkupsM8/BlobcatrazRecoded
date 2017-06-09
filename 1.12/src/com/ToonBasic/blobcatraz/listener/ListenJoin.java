package com.ToonBasic.blobcatraz.listener;

import static com.ToonBasic.blobcatraz.command.player.CommandHub.hub;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import com.ToonBasic.blobcatraz.utility.Util;

public class ListenJoin implements Listener {
	@EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        boolean noob = !p.hasPlayedBefore();
        if(noob) {
        	hub(p);
        	String msg = Util.prefix + Util.color("&dPlease welcome &f" + name + " &dto &3&lBlobcatraz&d!");
        	Util.broadcast(msg);
        	p.sendMessage(Util.color("&eDon't forget to do &a/rules"));
        } else {
        	String msg = Util.prefix + Util.color("&dWelcome back \u263b");
        	p.sendMessage(msg);
        }
        
        fireworks(p);
    }
	
	private void fireworks(Player p) {
		World w = p.getWorld();
        Location l = p.getLocation();
        
        Random rand = new Random();
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
        	FireworkMeta meta = fw.getFireworkMeta();
        	int rp = rand.nextInt(2) + 1;
        	meta.addEffect(fe);
        	meta.setPower(rp);
        	fw.setFireworkMeta(meta);  	
        }
	}
}