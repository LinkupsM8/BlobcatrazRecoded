package com.SirBlobman.blobcatraz.listener.sign;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class SignClickEvent extends BlockEvent {
    private static final HandlerList handlers = new HandlerList();
    private Player p;
	private Sign s;
	public SignClickEvent(Player p, Block b, Sign s) {super(b); this.p = p; this.s = s;}
	
	public Player getPlayer() {return p;}
	public String[] lines() {return s.getLines();}
	public String getLine(int i) {return s.getLine(i);}
	public void setLine(int i, String ss) {
		Block b = getBlock();
		BlockState bs = b.getState();
		Sign s = (Sign) bs;
		s.setLine(i, ss);
		s.update(true);
		this.s = s;
	}

	@Override
	public HandlerList getHandlers() {return handlers;}
    public static HandlerList getHandlerList() {return handlers;}
}