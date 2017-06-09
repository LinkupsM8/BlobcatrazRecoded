package com.ToonBasic.blobcatraz.utility;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import com.ToonBasic.blobcatraz.Core;

import me.clip.placeholderapi.PlaceholderAPI;

public class ScoreboardUtil extends Util implements Runnable {
	private static final Server SERVER = Bukkit.getServer();
	private static final BukkitScheduler BS = SERVER.getScheduler();
	private static final ScoreboardManager SM = SERVER.getScoreboardManager();
	
	public static void enable() {
		BS.runTaskTimer(Core.instance, new ScoreboardUtil(), 20L, 200L);
	}
	
	@Override
	public void run() {
		for(Player p : SERVER.getOnlinePlayers()) {
			if(disabled.contains(p)) continue;
			else update(p);
		}
	}
	
	private static List<Player> disabled = newList();
	private static Map<Player, Scoreboard> scores = newMap();
	public static void update(Player p) {
		if(disabled.contains(p)) {disabled.remove(p);}
		Scoreboard SB = getBoard(p);
		Objective custom = SB.getObjective("blobcatraz");
		custom.unregister();
		custom = SB.registerNewObjective("blobcatraz", "dummy");
		String disp = "&1&l&ki&3&lBlobcatraz&1&l&ki&f";
		custom.setDisplayName(color(disp));
		custom.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		List<String> list = normal(p);
		for(String s : list) {
			String entry = format(p, s);
			if(entry.length() > 40) entry = entry.substring(0, 40);
			Score sc = custom.getScore(entry);
			int i = (list.size() - list.indexOf(s));
			sc.setScore(i);
		}
		p.setScoreboard(SB);
		scores.put(p, SB);
	}
	
	private static List<String> normal(Player p) {
		List<String> list = newList(
			"&bCurrent Rank&c: &a%blobcatraz_current_rank%",
			"&bNext Rank&c: &a%rankup_next_rank%",
			"&bNext Rank Price&c: &a$%rankup_next_rank_cost_formatted%",
			"&bBalance&c: &a$%blobcatraz_balance%",
			"&bTokens&c: &a%blobcatraz_tokens%",
			"&bPing&c: &a" + PlayerUtil.getPing(p)
		);
		return list;
	}
	
	public static void remove(Player p) {
		disabled.add(p);
		Scoreboard MAIN = SM.getMainScoreboard();
		p.setScoreboard(MAIN);
	}
	
	private static String format(Player p, String o) {
		try {
			String c = PlaceholderAPI.setPlaceholders(p, o);
			c = color(c);
			c = c.replaceAll("#notinladder", "None");
			return c;
		} 
		catch(Throwable ex) {
			ex.printStackTrace();
			return color(o);
		}
	}
	
	public static Scoreboard getBoard(Player p) {
		if(scores.containsKey(p)) {
			Scoreboard SB = scores.get(p);
			return SB;
		} else {
			Scoreboard SB = SM.getNewScoreboard();
			Objective health = SB.registerNewObjective("Health", "health");
			health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			Objective custom = SB.registerNewObjective("blobcatraz", "dummy");
			custom.setDisplayName(color("&1&ki&3Blobcatraz&1&ki&f"));
			custom.setDisplaySlot(DisplaySlot.SIDEBAR);
			Team team = SB.registerNewTeam("noCollide");
			team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
			scores.put(p, SB);
			update(p);
			return getBoard(p);
		}
	}
	
	public static void afk(Player p) {
		Scoreboard SB = getBoard(p);
		Team team = SB.getTeam("noCollide");
		team.addEntry(p.getName());
		p.setCollidable(false);
	}
	
	public static void unAFK(Player p) {
		Scoreboard SB = getBoard(p);
		Team team = SB.getTeam("noCollide");
		team.removeEntry(p.getName());
		p.setCollidable(true);
	}
}