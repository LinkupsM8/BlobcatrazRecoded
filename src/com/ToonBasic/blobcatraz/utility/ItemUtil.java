package com.ToonBasic.blobcatraz.utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class ItemUtil extends Util
{
    /**
     * Checks if the {@link ItemStack} is air
     * @param is {@link ItemStack} to check
     * @return
     * <b>true</b> if the {@link ItemStack}'s type is AIR, or if it is null
     */
    public static boolean air(ItemStack is)
    {
        if(is == null) return true;
        Material mat = is.getType();
        Material air = Material.AIR;
        if(mat == air) return true;
        return false;
    }

    /**
     * Gets the name of an {@link ItemStack}
     * @param is {@link ItemStack} to get the name of
     * @return
     * If the {@link ItemStack} has a DisplayName, that will be returned
     * otherwise, the material and damage will be returned
     */
    public static String name(ItemStack is)
    {
        if(air(is)) return "AIR";
        ItemMeta meta = is.getItemMeta();
        boolean disp = meta.hasDisplayName();
        if(disp) return meta.getDisplayName();

        Material mat = is.getType();
        short dur = is.getDurability();
        boolean b1 = (dur == 0);
        boolean b2 = (dur == Short.MAX_VALUE);
        if(b1 || b2) return mat.name();
        return mat.name() + ":" + dur;
    }

    /**
     * Gets the lore of an {@link ItemStack}
     * @param is {@link ItemStack} to get the lore of
     * @return
     * Lore if the {@link ItemStack} has any<br/>
     * {@link Collections#emptyList()} if there is no lore
     */
    public static List<String> lore(ItemStack is)
    {
        if(air(is)) return Collections.emptyList();
        ItemMeta meta = is.getItemMeta();
        boolean lore = meta.hasLore();
        if(lore) return meta.getLore();
        return Collections.emptyList();
    }

    /**
     * Get the item that a player is holding
     * @param p Player to get the item of
     * @param off Off Hand?
     * @return {@link ItemStack} that the player is holding
     */
    public static ItemStack held(Player p, boolean off)
    {
        PlayerInventory pi = p.getInventory();
        ItemStack held = pi.getItemInMainHand();
        if(off) held = pi.getItemInOffHand();
        return held;
    }

    public static void rename(ItemStack is, String name)
    {
        if(air(is)) return;
        ItemMeta meta = is.getItemMeta();
        String disp = color(name);
        meta.setDisplayName(disp);
        is.setItemMeta(meta);
    }

    public static void setLore(ItemStack is, String lore)
    {
        if(air(is) || lore == null) return;
        ItemMeta meta = is.getItemMeta();
        String[] split = lore.split("/n");
        List<String> nlore = Collections.emptyList();
        for(String s : split)
        {
            String add = color(s);
            nlore.add(add);
        }
        meta.setLore(nlore);
        is.setItemMeta(meta);
    }

    public static void addLore(ItemStack is, String lore)
    {
        if(air(is) || lore == null) return;
        ItemMeta meta = is.getItemMeta();
        String[] split = lore.split("/n");
        List<String> nlore = meta.getLore();
        if(nlore == null) {setLore(is, lore); return;}
        for(String s : split)
        {
            String add = color(s);
            nlore.add(add);
        }
        meta.setLore(nlore);
        is.setItemMeta(meta);
    }
}