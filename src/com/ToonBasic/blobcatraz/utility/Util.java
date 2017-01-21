package com.ToonBasic.blobcatraz.utility;

import com.ToonBasic.blobcatraz.Blobcatraz;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;

public class Util
{
    protected static final Blobcatraz PLUGIN = Blobcatraz.instance;
	protected static final Server SERVER = Bukkit.getServer();
	protected static final BukkitScheduler SCHEDULER = SERVER.getScheduler();
	
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

	public static String json(String o)
    {
        String json = "{\"text\": \"" + o + "\"}";
        return json;
    }

    public static List<String> matching(List<String> o, String mat)
    {
        if(o == null || mat == null) return null;
        List<String> match = Collections.emptyList();
        for(String s : o)
        {
            String l = s.toLowerCase();
            String a = mat.toLowerCase();
            if(l.startsWith(a)) match.add(s);
        }
        return match;
    }

    public static String money(double d)
    {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        String money = nf.format(d);
        return money;
    }
}