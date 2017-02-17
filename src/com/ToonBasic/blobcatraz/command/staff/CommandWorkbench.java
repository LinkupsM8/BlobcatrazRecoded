package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Plugin created by Lilith
 * MC-Market: http://www.mc-market.org/members/16269/
 * Spigot: https://www.spigotmc.org/members/razorkings.32987/
 * Redistribution is not permitted
 * If you find any errors, please send me a PM on either Spigot or MC-Market.
 */
public class CommandWorkbench extends ICommand{
    public CommandWorkbench() {
        super("workbench", "", "blobcatraz.staff.workbench", "wb");
    }
    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(cs instanceof CraftPlayer) {
            Player player = cs.getServer().getPlayer(cs.getName());
            player.openWorkbench(null, true);
            player.sendMessage(prefix + "Now opening your workbench...");
        }
    }
}
