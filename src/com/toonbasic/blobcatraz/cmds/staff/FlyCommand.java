package com.toonbasic.blobcatraz.cmds.staff;

import com.toonbasic.blobcatraz.PublicHandlers;
import com.toonbasic.blobcatraz.cmds.ICommand;
import com.toonbasic.blobcatraz.cmds.ICommand.PlayerOnly;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@PlayerOnly
public class FlyCommand extends ICommand {
    String prefix = PublicHandlers.prefix;

    public FlyCommand() {
        super("fly", "", "blobcatraz.staff.fly");
    }
    @Override
    public void handleCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Missing arguments! Did you mean /fly [on/off]?");
        }
        if(args[1].equalsIgnoreCase("on")) {
            Player p = (Player) sender;
            p.setFlying(true);
            p.sendMessage(prefix + "§fYou have enabled fly!");
            } else {
            if(args[1].equalsIgnoreCase("off")) {
                Player p = (Player) sender;
                p.setFlying(false);
                p.sendMessage(prefix + "§fYou have disabled fly!");
            }
        }
    }
}
