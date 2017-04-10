package com.ToonBasic.blobcatraz.command.player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.utility.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static com.ToonBasic.blobcatraz.utility.Util.color;

public class CommandReply extends ICommand {

    public static HashMap<CommandSender, CommandSender> getMSG = new HashMap<CommandSender, CommandSender>();

    public CommandReply() {
        super("reply", "/reply <message>", "blobcatraz.player.msg", "r");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if (args.length > 0) {
            Player t = Bukkit.getPlayer(args[0]);
                if (getMSG.containsKey(t)) {
                    CommandSender s = getMSG.get(t);
                    String msg = Util.finalArgs(1, args);
                    cs.sendMessage(color("&7[&aMe &f-> &b" + t.getName() + "&7] &f" + msg));
                    s.sendMessage(color("&7[&b" + cs.getName() + " &f-> &a" + t.getName() + "&7] &f" + msg));
                }
            }
        }
    }