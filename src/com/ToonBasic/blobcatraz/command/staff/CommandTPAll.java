package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandTPAll extends ICommand {
    public CommandTPAll() {
        super("tpall", "", "blobcatraz.staff.tpall", "bringserver");
    }
    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        p.sendMessage(prefix + "You have teleported the entire server to yourself.");
        for(Player on : Bukkit.getOnlinePlayers()) {
            if(on != p) {
                if(on.getWorld() == p.getWorld()) {
                    on.teleport(p);
                    on.sendMessage(prefix + "You have been summoned by the gods!");
                }
            }
        }
    }
}
