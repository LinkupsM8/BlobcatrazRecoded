package com.ToonBasic.blobcatraz.command.staff;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandSetMOTD extends ICommand implements Listener {
	private static final File file = new File(Core.folder, "motd.txt");
	private static YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
	
	public CommandSetMOTD() {super("setmotd", "<motd>", "blobcatraz.staff.setmotd");}
	
	@Override
	public void handleCommand(CommandSender cs, String[] args) {
		if(args.length > 0) {
			String motd = Util.finalArgs(0, args);
			motd = motd.replace("\\n", "\n");
			motd = motd.replace("/n", "\n");
			setMOTD(motd);
			cs.sendMessage("You changed the MOTD to: ");
			cs.sendMessage(getMOTD());
		}
	}
	
	@EventHandler
	public void motd(ServerListPingEvent e) {
		String motd = getMOTD();
		e.setMotd(motd);
	}
	
	private void create() throws IOException {
		file.getParentFile().mkdirs();
		file.createNewFile();
		config.set("motd", "&5Example MOTD");
		config.save(file);
	}
	
	private void setMOTD(String motd) {
		try {
			if(!file.exists()) create();
			motd = motd.replace('\u00a7', '&');
			config.set("motd", motd);
			config.save(file);
		} catch(Exception ex) {
			String error = "Failed to set MOTD: " + ex.getMessage();
			Util.print(error);
		}
	}
	
	private String getMOTD() {
		try {
			if(!file.exists()) create();
			config.load(file);
			String m = config.getString("motd");
			String motd = Util.color(m);
			return motd;
		} catch(Exception ex) {
			String error = "Failed to get MOTD: " + ex.getMessage();
			Util.print(error);
			return Util.color("&4Error: Failed to get MOTD\nContact an Admin!");
		}
	}
}