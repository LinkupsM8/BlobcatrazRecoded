package com.toonbasic.blobcatraz.cmds.staff;

import com.toonbasic.blobcatraz.PublicHandlers;
import com.toonbasic.blobcatraz.cmds.ICommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@ICommand.PlayerOnly
public class FlyCommand extends ICommand {
    String prefix = PublicHandlers.prefix;
    public FlyCommand() {
        super("fly", "", "blobcatraz.staff.fly");
    }
    public void handleCommand(CommandSender sender, String[] args) {
        if(args.length == 0) {
            Player p = (Player) sender;
            if(p.isFlying()) {
                p.setFlying(false);
                p.sendMessage(prefix + "Flying has been disabled!");
            } else {
                p.setFlying(true);
                p.sendMessage(prefix + "Flying has been enabled!");
            }
        } else {
            if(args.length > 0) {
                String target = args[0];
                Player t = Bukkit.getPlayer(target); {
                    if(t == null) {
                        sender.sendMessage(prefix + "§cSorry, the player §f" + t.getName() + " §is not online, or doesn't exist!");
                    } else {
                        if(t.isFlying()) {
                            t.setFlying(false);
                            t.sendMessage(prefix + "§fFly has been disabled by §c" + sender.getName());
                            sender.sendMessage(prefix + "§fYou have disabled Fly for §c" + t.getName());
                        } else {
                            t.setFlying(true);
                            t.sendMessage(prefix + "§fFly has been enabled by §c" + sender.getName());
                            sender.sendMessage(prefix + "§fYou have enabled Fly for §c" + t.getName());
                        }
                    }
                }
            }
        }
    }
}
