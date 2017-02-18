package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandBan extends ICommand {
    public CommandBan() {super("ban", "<name> <reason>", "blobcatraz.staff.ban");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length > 1) {
            String target = args[0];
            String reason = Util.finalArgs(1, args);

            Player p = Bukkit.getPlayer(target);
            if(p != null) {
                BanList bl = Bukkit.getBanList(Type.NAME);
                String nreason = Util.color("&f" + reason + "\n&f&lBanned By: &f" + cs.getName() + "\n&f&lAppeal At: &f&3&o&nhttp://blobcatraz.mc-srv.com");
                bl.addBan(p.getName(), nreason, null, cs.getName());
                p.kickPlayer(Util.color("&4You are banned!" + "\n&fReason: &r") + nreason);
            } else {
				String error = String.format(Language.INVALID_TARGET, target);
				cs.sendMessage(error);
            }
        }
    }
}
