package com.ToonBasic.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class FlyCMD extends Manager 
{
	public FlyCMD() {super("fly", "blobcatraz.fly", false);}
	
	@Override
	public void execute(CommandSender cs, String[] args) 
	{
		super.execute(cs, args);
		if(args.length == 0)
		{
			if(cs instanceof Player)
			{
				Player p = (Player) cs;
				if(p.isFlying())
				{
					p.setFlying(false);
					p.sendMessage(PREFIX + "Flying is now disabled!");
				}
				else
				{
					p.setFlying(true);
					p.sendMessage(PREFIX + "Flying is now enabled");
				}
			}
			else
			{
				String msg = PREFIX + "You are not a player";
				cs.sendMessage(msg);
			}
		}
		else if(args.length > 0)
		{
			String target = args[0];
			Player p = Bukkit.getPlayer(target);
			if(p.isFlying())
			{
				p.setFlying(false);
				p.sendMessage(PREFIX + "Flying is now disabled!");
				cs.sendMessage(PREFIX + "Flying is now disabled for " + p.getName());
			}
			else
			{
				p.setFlying(true);
				p.sendMessage(PREFIX + "Flying is now enabled");
				cs.sendMessage(PREFIX + "Flying is now enabled for " + p.getName());
			}
		}
	}
}