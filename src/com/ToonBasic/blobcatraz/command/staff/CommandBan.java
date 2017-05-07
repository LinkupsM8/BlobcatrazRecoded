package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;

public class CommandBan extends ICommand {
    public CommandBan() {super("ban", "<name> <reason>", "blobcatraz.staff.ban");}

    @Override
    @SuppressWarnings("deprecation")
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length > 1) {
            String target = args[0];
            String reason = Util.finalArgs(1, args);

			OfflinePlayer op = Bukkit.getOfflinePlayer(target);
            if(op != null) {
            	String banner = cs.getName();
                ban(op, banner, reason);
            } else {
				String error = String.format(Language.INVALID_TARGET, target);
				cs.sendMessage(error);
            }
        }
    }
    
    private static final String SITE = Util.color("&3&o&nhttp://blobcatraz.mc-srv.com");
    public static void ban(OfflinePlayer op, String banner, String reason) {
    	BanList bl = Bukkit.getBanList(Type.NAME);
    	String nreason = Util.color("&f" + reason + "\n"
    			+ "&b&lBanned By: &f" + banner + "\n"
    			+ "&b&lAppeal At: &f" + SITE);
    	String name = op.getName();
    	bl.addBan(name, nreason, null, banner);
    	if(op.isOnline()) {
    		Player p = op.getPlayer();
    		p.kickPlayer(Util.color("&4&lYou are now banned!" + "\n&b&lReason: &r" + nreason));
    		String bcast = Util.prefix + Util.color("&a&l" + name + " &ewas banned by " + banner + " for:\n");
    		Util.broadcast(bcast, Util.color(reason));
    	}
    }
}
