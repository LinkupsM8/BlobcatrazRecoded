package com.ToonBasic.blobcatraz.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class GameModeCMD extends Manager {

	String prefix = "§3{§bBlobcatraz§3} §f";
	public GameModeCMD() {super("gm", "blobcatraz.gamemode", false);}

	@Override
	public final void execute(CommandSender cs, String[] args)
	{
	    super.execute(cs, args);
	    if(cs instanceof Player)
        {
            Player p = (Player) cs;
            if(args.length >= 1)
            {
                Player target = p;
                GameMode gamemode = GameMode.SURVIVAL;
                if(args.length > 1)
                {
                    String name = args[1];
                    target = Bukkit.getPlayer(name);
                    if(target == null)
                    {
                        p.sendMessage(prefix + "Invalid Target: " + name);
                        return;
                    }
                }

                String gm = args[0].toUpperCase();
                gamemode = match(gm);
                target.setGameMode(gamemode);
                target.sendMessage(prefix + "Your GameMode was changed to " + gamemode.name());
                p.sendMessage(prefix + "Success!");
            }
            else
            {
                p.sendMessage(prefix + "Missing Arguments!");
                p.sendMessage("Usage: /gm [a/c/s/sp]");
            }
        }
        else
        {
            cs.sendMessage("You are not a Player");
            return;
        }
	}

	private GameMode match(String gm)
    {
        switch(gm)
        {
            case "0":
            case "S":
            case "SURVIVAL":
                return GameMode.SURVIVAL;
            case "1":
            case "C":
            case "CREATIVE":
                return GameMode.CREATIVE;
            case "2":
            case "A":
            case "ADVENTURE":
                return GameMode.ADVENTURE;
            case "3":
            case "SP":
            case "SPECTATOR":
                return GameMode.SPECTATOR;
            default: return GameMode.SURVIVAL;
        }
    }
}
