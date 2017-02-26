package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

@ICommand.PlayerOnly
public class CommandTPAll extends ICommand {
    public CommandTPAll() {
        super("tpall", "", "blobcatraz.staff.tpall", "bringserver");
    }
    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        p.sendMessage(prefix + "You have teleported the entire server to yourself.");
        for(Player getOnline : Bukkit.getOnlinePlayers()) {
            if(getOnline != p) {
                if(getOnline.getLocation().getWorld() == p.getLocation().getWorld()) {
                    getOnline.teleport(p);
                    getOnline.sendMessage(prefix + "You have been summoned by the gods!");
                }
            }
        }
    }
}
