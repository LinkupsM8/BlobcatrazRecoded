package com.SirBlobman.blobcatraz.utility;

import java.util.List;
import java.util.Map;

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

import com.SirBlobman.blobcatraz.Core;
import com.SirBlobman.blobcatraz.config.ConfigDatabase;

import me.clip.placeholderapi.PlaceholderAPI;

public class ScoreboardUtil extends Util implements Runnable {
	private static final BukkitScheduler BS = SERVER.getScheduler();
	private static final ScoreboardManager SM = SERVER.getScoreboardManager();
	
	private static List<Player> disabled = newList();
	private static Map<Player, Scoreboard> scores = newMap();
	
	public static void enable() {
		Runnable r = new ScoreboardUtil();
		BS.runTaskTimer(Core.INSTANCE, r, 20L, 200L);
	}

	public static void disable(Player p) {disabled.add(p);}
	
	@Override
	public void run() {
		for(Player p : SERVER.getOnlinePlayers()) {
			if(disabled.contains(p)) continue;
			else update(p);
		}
	}
	
	public static Scoreboard board(Player p) {
		if(scores.containsKey(p)) {
			Scoreboard sb = scores.get(p);
			return sb;
		} else {
			Scoreboard sb = SM.getNewScoreboard();
			Objective health = sb.registerNewObjective("Health", "health");
			health.setDisplaySlot(DisplaySlot.PLAYER_LIST);
			
			Objective custom = sb.registerNewObjective("blobcatraz", "dummy");
			String disp = color("&1&l&ki&3&lBlobcatraz&1&l&ki&f");
			custom.setDisplayName(disp);
			custom.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			Team team = sb.registerNewTeam("noCollide");
			team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
			
			scores.put(p, sb);
			return board(p);
		}
	}
	
	public static void update(Player p) {
		if(disabled.contains(p)) {disabled.remove(p);}
		Scoreboard SB = board(p);
		Objective custom = SB.getObjective("blobcatraz");
		custom.unregister();
		custom = SB.registerNewObjective("blobcatraz", "dummy");
		String disp = color("&1&l&ki&3&lBlobcatraz&1&l&ki&f");
		custom.setDisplayName(disp);
		custom.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		List<String> list = list(p);
		for(String s : list) {
			String e = color(s);
			if(e.length() > 40) e = e.substring(0, 40);
			Score sc = custom.getScore(e);
			int i = (list.size() - list.indexOf(s));
			sc.setScore(i);
		}
		p.setScoreboard(SB);
		scores.put(p, SB);
	}
	
	private static String format(Player p, String o, String r1, String r2) {
		String c = o;
		try {
			String re = PlaceholderAPI.setPlaceholders(p, r1);
			c = c.replaceAll("\\{0\\}", re);
		} catch(Throwable ex) {c = c.replaceAll("\\{0\\}", r2);}
		c = color(c);
		c = c.replaceAll("#notinladder", "None");
		return c;
	}
	
	private static List<String> list(Player p) {
		String curr = format(p, "&bCurrent Rank&c: &a{0}", "%blobcatraz_current_rank%", VaultUtil.mainRank(p));
		String next = format(p, "&bNext Rank&c: &a{0}", "%rankup_next_rank%", "None");
		String pric = format(p, "&bNext Rank Price&c: &a${0}", "rankup_next_rank_cost_formatted%", "0.00");
		String bala = format(p, "&bBalance&c: &a${0}", "%blobcatraz_balance%", NumberUtil.cropDecimal(VaultUtil.balance(p), 2));
		String toke = format(p, "&bTokens&c: &a{0}", "%blobcatraz_tokens%", str(ConfigDatabase.tokens(p)));
		String ping = format(p, "&bPing&c: &a{0}", PlayerUtil.getPing(p), PlayerUtil.getPing(p));
		List<String> list = newList(curr, next, pric, bala, toke, ping);
		return list;
	}
	
	public static void afk(Player p) {
		String name = p.getName();
		Scoreboard sb = board(p);
		Team team = sb.getTeam("noCollide");
		team.addEntry(name);
		p.setCollidable(false);
	}
	
	public static void unAFK(Player p) {
		String name = p.getName();
		Scoreboard sb = board(p);
		Team team = sb.getTeam("noCollide");
		team.removeEntry(name);
		p.setCollidable(true);
	}
}