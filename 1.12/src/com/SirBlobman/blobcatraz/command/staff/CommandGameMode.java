package com.SirBlobman.blobcatraz.command.staff;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.SirBlobman.blobcatraz.command.PlayerCommand;
import com.SirBlobman.blobcatraz.utility.Util;
import com.SirBlobman.blobcatraz.utility.WordUtil;

public class CommandGameMode extends PlayerCommand {
	List<String> weird = Util.newList("gma", "gmc", "gms", "gmsp");
	public CommandGameMode() {super("gm", "[gamemode] [player]", "blobcatraz.staff.gamemode", "gamemode", "gmc", "gms", "gma", "gmsp");}
	
	@Override
	public void run(Player p, String[] args) {
		String mode = "s";
		String target = null;
		Player t = p;
		if(weird.contains(getCommandUsed().toLowerCase())) {
			mode = getCommandUsed();
			if(args.length > 0) target = args[0];
		} else {
			if(args.length > 0) {
				mode = args[0];
				if(args.length > 1) target = args[1];
			}
		}
		
		if(target != null) {
			t = Bukkit.getPlayer(target);
			if(t == null) {
				String error = Util.format(prefix + Language.INVALID_TARGET, target);
				p.sendMessage(error);
				t = p;
			}
		}
		
		GameMode gm = GameMode.SURVIVAL;
		gm = match(mode);
		t.setGameMode(gm);
		String game = Util.str(gm);
		String tname = t.getName();
		String pname = p.getName();
		String poss = WordUtil.possessive(tname);
		String msg1 = Util.format(prefix + "Your gamemode was changed to &e%1s", game);
		String msg2 = Util.format(prefix + "You changed &a%1s&r gamemode to &e%2s", poss, game);
		t.sendMessage(msg1);
		if(!tname.equals(pname)) p.sendMessage(msg2);
	}
	
	private GameMode match(String gm) {
		String u = gm.toUpperCase();
		switch(u) {
		case "0":
		case "S":
		case "SURVIVAL":
		case "GMS":
			return GameMode.SURVIVAL;
		case "1":
		case "C":
		case "CREATIVE":
		case "GMC":
			return GameMode.CREATIVE;
		case "2":
		case "A":
		case "ADVENTURE":
		case "GMA":
			return GameMode.ADVENTURE;
		case "3":
		case "SP":
		case "SPECTATOR":
		case "GMSP":
			return GameMode.SPECTATOR;
		default: 
			return GameMode.SURVIVAL;
		}
	}
}