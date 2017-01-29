package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;

/**
 * Plugin created by Lilith
 * Plugin redistribution is not permitted without proper authorization
 * You shall not claim any of my open sourced plugins as your own even if you modify the code to your liking
 * Made on 1/28/2017
 */
@ICommand.PlayerOnly
public class CommandVanish extends ICommand implements Listener {
    private ArrayList<Player> vanish = new ArrayList<Player>();
    public CommandVanish() {super("vanish", "", "blobcatraz.staff.vanish", "v");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        if(vanish.contains(p)) {
            for(Player players : Bukkit.getOnlinePlayers()) {
                players.showPlayer(p);
            }
            vanish.remove(p);
            p.sendMessage(prefix + "You are no longer in vanish!");
        } else {
            for(Player players : Bukkit.getOnlinePlayers()) {
                vanish.add(p);
                players.hidePlayer(p);
                p.sendMessage(prefix + "You are now in vanish!");
            }
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent e) {
        vanish.remove(e.getPlayer());
        }
    }
