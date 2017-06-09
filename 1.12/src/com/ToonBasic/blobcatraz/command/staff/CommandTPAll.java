package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandTPAll extends ICommand {
    public CommandTPAll() {super("tpall", "", "blobcatraz.staff.tpall", "bringserver");}
    
    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        p.sendMessage(prefix + "Teleporting the entire server to yourself...");
        for(Player o : Bukkit.getOnlinePlayers()) {
        	boolean equal = o.equals(p);
        	World w1 = p.getWorld();
        	World w2 = o.getWorld();
        	if(w1.equals(w2) && !equal) {
        		o.teleport(p);
        		o.sendMessage(prefix + "You were summoned by the gods!");
        	}
        }
    }
}
