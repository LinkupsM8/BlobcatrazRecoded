package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSmite extends ICommand {
    public CommandSmite() {
        super("smite", "<player>", "blobcatraz.staff.smite", "lightning", "zap", "thor");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            target.getWorld().strikeLightning(target.getLocation());
            cs.sendMessage(prefix + "You have smited " + target.getName() + "!");
        }
    }
}
