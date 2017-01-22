package com.ToonBasic.blobcatraz;

import org.bukkit.ChatColor;

public class PublicHandlers {
    public static String prefix = color("&3{&bBlobcatraz&3} &f");

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
