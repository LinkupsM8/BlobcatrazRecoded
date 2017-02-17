package com.ToonBasic.blobcatraz.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.ToonBasic.blobcatraz.command.ICommand;
import com.ToonBasic.blobcatraz.command.ICommand.PlayerOnly;

import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.IInventory;

@PlayerOnly
public class CommandFurnace extends ICommand {
    public CommandFurnace() {super("furnace", "", "blobcatraz.staff.furnace", "smelt");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        Inventory furn = Bukkit.createInventory(p, InventoryType.FURNACE);
        CraftInventory ci = (CraftInventory) furn;
        IInventory ii = ci.getInventory();
        if(p instanceof CraftPlayer) {
        	CraftPlayer cp = (CraftPlayer) p;
        	EntityPlayer ep = cp.getHandle();
        	ep.openContainer(ii);
            cp.sendMessage(prefix + "Opening a furnace...");
        }
    }
}
