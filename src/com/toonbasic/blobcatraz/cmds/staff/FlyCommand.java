package com.toonbasic.blobcatraz.cmds.staff;

import com.toonbasic.blobcatraz.cmds.ICommand;
import com.toonbasic.blobcatraz.cmds.ICommand.PlayerOnly;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@PlayerOnly
public class FlyCommand extends ICommand {
    public FlyCommand() {
        super("fly", "", "blobcatraz.staff.fly");
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
            } else if(arg.equals("off"))
            {
                p.setFlying(false);
                p.setAllowFlight(false);
                p.sendMessage(prefix + "§fYou have disabled flying!");
            } else {

            }
        } else {
            cs.sendMessage("Missing Arguments: Did you mean /fly [on/off]");
        }
    }
}
