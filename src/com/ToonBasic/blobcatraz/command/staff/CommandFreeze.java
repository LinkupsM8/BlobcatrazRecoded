package com.ToonBasic.blobcatraz.command.staff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandFreeze extends ICommand implements Listener {
    private static List<Player> frozen = new ArrayList<Player>();
    public CommandFreeze() {super("freeze", "<name>", "blobcatraz.staff.freeze");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            frozen.add(target);
            target.sendMessage(prefix + "You are now frozen! You will not be able to move!");
            cs.sendMessage(prefix + "You have frozen " + target.getName());
        }
    }
    
    @EventHandler
    public void onPlayerFreeze(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (frozen.contains(p)) {
            e.setCancelled(true);
            p.sendMessage(prefix + "You are frozen! You cannot move!");
        }
    }
}
