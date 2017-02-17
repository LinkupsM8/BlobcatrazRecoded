package com.ToonBasic.blobcatraz.command.staff;

import com.ToonBasic.blobcatraz.command.ICommand;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class CommandWorkbench extends ICommand{
    public CommandWorkbench() {
        super("workbench", "", "blobcatraz.staff.workbench", "wb", "craft");
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
