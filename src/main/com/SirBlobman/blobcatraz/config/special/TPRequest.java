package com.SirBlobman.blobcatraz.config.special;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TPRequest {
	private Player sender, who, t;
	Location tp;
	public TPRequest(Player sender, Player who, Location tp) {
		this.sender = sender;
		this.who = who;
		this.tp = tp;
	}
	
	public TPRequest(Player sender, Player who, Player t) {
		this.sender = sender;
		this.who = who;
		this.t = t;
	}
	
	public Player sender() {return sender;}
	public Player who() {return who;}
	public Location location() {
		if(tp == null) {
			Location l = t.getLocation();
			return l;
		} else return tp;
	}
}