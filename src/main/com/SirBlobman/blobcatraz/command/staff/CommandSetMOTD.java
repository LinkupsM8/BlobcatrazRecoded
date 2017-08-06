package com.SirBlobman.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import com.SirBlobman.blobcatraz.command.ICommand;
import com.SirBlobman.blobcatraz.config.ConfigBlobcatraz;
import com.SirBlobman.blobcatraz.utility.Util;

public class CommandSetMOTD extends ICommand implements Listener {
	public CommandSetMOTD() {super("setmotd", "<motd>", "blobcatraz.staff.setmotd", "changemotd");}
	
	@Override
	public void run(CommandSender cs, String[] args) {
		String motd = Util.finalArgs(0, args);
		motd = motd.replace("\\n", "\n").replace("/n", "\n");
		set(motd);
		String[] msg = Util.color(
			prefix + "You changed the MOTD to:",
			get()
		);
		cs.sendMessage(msg);
	}
	
	@EventHandler
	public void motd(ServerListPingEvent e) {
		int num = e.getNumPlayers();
		int max = num + 1;
		e.setMaxPlayers(max);
		String motd = get();
		if(motd == null) motd = Util.color("&5Example MOTD");
		e.setMotd(motd);
	}
	
	private void set(String motd) {
		String s = motd.replace('\u00a7', '&');
		ConfigBlobcatraz.load();
		ConfigBlobcatraz.set("motd", s, true);
		ConfigBlobcatraz.save();
	}
	
	private String get() {
		String motd = ConfigBlobcatraz.get(String.class, "motd");
		String c = Util.color(motd);
		return c;
	}
}