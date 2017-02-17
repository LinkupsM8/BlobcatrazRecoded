package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Plugin created by Lilith
 * MC-Market: http://www.mc-market.org/members/16269/
 * Spigot: https://www.spigotmc.org/members/razorkings.32987/
 * Redistribution is not permitted
 * If you find any errors, please send me a PM on either Spigot or MC-Market.
 */
@ICommand.PlayerOnly
public class CommandClearInventory extends ICommand {
    public CommandClearInventory() {
        super("clear", "", "blobcatraz.staff.clearinv", "ci");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player player = (Player) cs;
        player.getInventory().clear();
        player.sendMessage(prefix + "You have cleared your inventory!");
    }
}
