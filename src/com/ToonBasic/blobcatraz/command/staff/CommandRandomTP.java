package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

/**
 * Plugin created by Lilith
 * Plugin redistribution is not permitted without proper authorization
 * You shall not claim any of my open sourced plugins as your own even if you modify the code to your liking
 * Made on 1/28/2017
 */
public class CommandRandomTP extends ICommand {
    ArrayList<Player> players = new ArrayList<Player>();
    private static final Random random = new Random();
    public CommandRandomTP() {super("randomtp", "", "blobcatraz.player.randomtp", "rtp");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        for(Player randomPlayer : Bukkit.getOnlinePlayers()) players.add(randomPlayer);
        Player getPlayer = players.get(new Random().nextInt(players.size()));
        p.teleport(getPlayer.getLocation());
        cs.sendMessage(prefix + "You have teleported to " + getPlayer.getName());
    }
}
