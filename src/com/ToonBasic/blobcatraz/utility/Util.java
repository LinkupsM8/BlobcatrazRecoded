package com.ToonBasic.blobcatraz.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;

public class Util
{
	private static final Server SERVER = Bukkit.getServer();
	
	/**
	 * show a message in the console<br/>
	 * will also display in the log files
	 * @param msg Message to send
	 */
	public static void print(String msg)
	{
		CommandSender cs = SERVER.getConsoleSender();
		cs.sendMessage(msg);
	}
	
	/**
	 * Add some color/formatting to a message
	 * @param msg Message to color
	 * @return Colored String (&amp; to &sect;)<br/>
	 * Example:<br/>
	 * &4Red to <span style="color:red">Red</span>
	 */
	public static String color(String msg)
	{
		String c = ChatColor.translateAlternateColorCodes('&', msg);
		return c;
	}
	
	/**
	 * Remove all color and formatting from a message
	 * @param msg Message to unformat
	 * @return Uncolored String (&sect; to &amp;)
	 */
	public static String strip(String msg)
	{
		String s = ChatColor.stripColor(msg);
		return s;
	}
}