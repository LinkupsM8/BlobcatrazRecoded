package com.ToonBasic.blobcatraz.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.ToonBasic.blobcatraz.utility.Util;

public class ListenSignColor implements Listener {
	@EventHandler
	public void sign(SignChangeEvent e) {
		String[] lines = e.getLines();
		String[] color = Util.color(lines);
		e.setLine(0, color[0]);
		e.setLine(1, color[1]);
		e.setLine(2, color[2]);
		e.setLine(3, color[3]);
	}
}