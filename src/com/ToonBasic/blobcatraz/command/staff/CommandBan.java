package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.PublicHandlers;
import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandBan extends ICommand {
    public CommandBan() {super("ban", "", "blobcatraz.staff.ban");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length > 1) {
            String target = args[0];
            String reason = PublicHandlers.finalArgs(1, args);
            reason = PublicHandlers.color(reason);

            Player p = Bukkit.getPlayer(target);
            if(p != null) {
                BanList bl = Bukkit.getBanList(Type.NAME);
                String nreason = PublicHandlers.color("&4You are banned!" + "\n&fReason: &r" + reason + "\n&fBanned By: " + cs.getName() + "\n&fAppeal At: &3&o&nhttp://blobcatraz.mc-srv.com");
                bl.addBan(p.getName(), nreason, null, cs.getName());
                p.kickPlayer(nreason);
            } else {
                cs.sendMessage("§3" + target + " §ris not a Player");
            }
        }
    }
}
