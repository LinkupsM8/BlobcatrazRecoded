package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandGamemode extends ICommand {
    public CommandGamemode() {super("gm", "<gamemode>", "blobcatraz.staff.gamemode", "gamemode");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        if(args.length > 0) {
            GameMode gamemode = GameMode.SURVIVAL;
            String gm = args[0];
            gamemode = match(gm);
            p.setGameMode(gamemode);
            p.sendMessage(prefix + "Your GameMode was changed to " + gamemode.name());
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