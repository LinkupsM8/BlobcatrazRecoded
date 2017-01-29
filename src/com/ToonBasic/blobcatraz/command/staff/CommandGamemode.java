package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.PublicHandlers;
import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandGamemode extends ICommand {
    public CommandGamemode() {super("gm", "<gamemode> [player]", "blobcatraz.staff.gamemode", "gamemode");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        Player t = p;
        if(args.length > 0) {
            if(args.length > 1) {
            	t = Bukkit.getPlayer(args[0]);
            	if(t == null) t = p;
            }
            
            GameMode gamemode = GameMode.SURVIVAL;
            String gm = args[0];
            gamemode = match(gm);
            t.setGameMode(gamemode);
            t.sendMessage(prefix + PublicHandlers.color("Your GameMode was changed to &e" + gamemode.name()));
            if(!t.getName().equals(p.getName())) p.sendMessage(prefix + PublicHandlers.color("&d" + t.getName() + " &f's GameMode was changed to &e" + gamemode.name()));
        } else {
            p.sendMessage("Missing Arguments: Did you mean /gm [gamemode]");
        }
    }

    private GameMode match(String gm) {
        gm = gm.toUpperCase();
        switch(gm) {
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