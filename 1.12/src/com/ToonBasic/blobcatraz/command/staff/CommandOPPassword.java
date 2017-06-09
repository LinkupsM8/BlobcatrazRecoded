package com.ToonBasic.blobcatraz.command.staff;

import java.io.File;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandOPPassword extends ICommand {
	private static List<CommandSender> unlocked = Util.newList();
	private static final String password = password();
	public CommandOPPassword() {super("oppassword", "<password>", "blobcatraz.staff.op", "operatorpassword", "addoppassword");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		String word = Util.finalArgs(0, args);
		if(word.equals(password)) {
			unlocked.add(cs);
			cs.sendMessage("&2&lThe password is correct, you may now OP a player");
		} else {
			if(unlocked.contains(cs)) unlocked.remove(cs);
			String error = Util.color("&4&lWRONG PASSWORD!");
			if(cs instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) cs;
				le.setHealth(0.0D);
				le.sendMessage(error);
				if(le instanceof Player) {
					Player p = (Player) cs;
					p.kickPlayer(error);
				}
			}
		}
	}
	
	private static final String password() {
		try {
			File file = new File(Core.folder, "password.yml");
			if(!file.exists()) {
				Core.folder.mkdirs();
				file.createNewFile();
			}
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.load(file);
			String password = config.getString("password");
			return password;
		} catch(Exception ex) {
			return "ASecretPassword";
		}
	}
	
	public static boolean allowedToOP(CommandSender cs) {
		boolean b = unlocked.contains(cs);
		return b;
	}
	
	public static void remove(CommandSender cs) {
		unlocked.remove(cs);
	}
}