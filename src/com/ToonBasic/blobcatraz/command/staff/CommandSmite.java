package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Plugin created by Lilith
 * MC-Market: http://www.mc-market.org/members/16269/
 * Spigot: https://www.spigotmc.org/members/razorkings.32987/
 * Redistribution is not permitted
 * If you find any errors, please send me a PM on either Spigot or MC-Market.
 */
public class CommandSmite extends ICommand {
    public CommandSmite() {
        super("smite", "<player>", "blobcatraz.staff.smite", "lightning");
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
