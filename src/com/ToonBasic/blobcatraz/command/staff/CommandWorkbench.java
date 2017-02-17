package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;

import com.ToonBasic.blobcatraz.command.ICommand;

public class CommandWorkbench extends ICommand{
    public CommandWorkbench() {super("workbench", "", "blobcatraz.staff.workbench", "wb", "craft");}
    
    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        if(cs instanceof CraftPlayer) {
            CraftPlayer cp = (CraftPlayer) cs;
            cp.openWorkbench(null, true);
            cp.sendMessage(prefix + "Now opening your workbench...");
        }
    }
}
