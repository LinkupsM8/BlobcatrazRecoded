package com.ToonBasic.blobcatraz.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.ToonBasic.blobcatraz.utility.Util;

public class ListenJoin implements Listener {
	private static final Scoreboard sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
	private static final Team team = sb.registerNewTeam("AntiCollideTeam");
	
	@EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        boolean noob = !p.hasPlayedBefore();
        if(noob) {
        	String msg = Util.prefix + Util.color("&dPlease welcome &f" + name + " &dto &3&lBlobcatraz&d!");
        	Util.broadcast(msg);
        	p.sendMessage(Util.color("&eDon't forget to do &a/rules"));
        } else {
        	String msg = Util.prefix + Util.color("&dWelcome back \u263b");
        	p.sendMessage(msg);
        }

        team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
        team.addEntry(p.getName());
        p.setScoreboard(sb);
    }
}
