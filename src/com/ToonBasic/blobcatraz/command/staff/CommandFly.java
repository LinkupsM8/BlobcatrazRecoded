package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

@PlayerOnly
public class CommandFly extends ICommand {
    public CommandFly() {
        super("fly", "<on/off>", "blobcatraz.staff.fly");
    }

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(args.length > 0) {
            String arg = args[0].toLowerCase();
            Player p = (Player) cs;
            if(arg.equals("on")) {
                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage(prefix + "§fYou have enabled flying!");
            } else if(arg.equals("off")) {
                p.setFlying(false);
                p.setAllowFlight(false);
                p.sendMessage(prefix + "§fYou have disabled flying!");
            } else {
                p.sendMessage("Invalid Arguments: Did you mean /fly [on/off]");
            }
        } else {
            cs.sendMessage("Missing Arguments: Did you mean /fly [on/off]");
        }
    }
}