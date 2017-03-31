package com.ToonBasic.blobcatraz.command.player;

import static com.ToonBasic.blobcatraz.command.staff.CommandVanish.vanished;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.ToonBasic.blobcatraz.command.ICommand;

import net.md_5.bungee.api.ChatColor;

public class CommandStaff extends ICommand {
    public CommandStaff() {super("staff", "", "blobcatraz.player.staff");}

    @Override
    public void handleCommand(CommandSender cs, String[] args) {
        Player p = (Player) cs;
        p.sendMessage(prefix + "Now opening the staff gui...");
        makeGUI(p);
    }

    public void makeGUI(Player p) {
        Inventory heads = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Online Staff: ");

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        int i = 0;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("blobcatraz.staff.check")) {
                if (vanished.contains(player)) {
                    //Do not add player
                } else {
                    meta.setOwner(p.getName());
                    meta.setDisplayName(ChatColor.LIGHT_PURPLE + p.getName());
                    skull.setItemMeta(meta);
                    heads.setItem(i, skull);
                    i++;
                }
            }
        }
        p.openInventory(heads);
    }
}
