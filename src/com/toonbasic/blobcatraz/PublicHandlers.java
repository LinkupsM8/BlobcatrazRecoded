package com.toonbasic.blobcatraz;

import net.md_5.bungee.api.ChatColor;

public class PublicHandlers {
    public static String prefix = "§3{§bBlobcatraz§3} ";

    public static void color(String msg) {
        ChatColor.translateAlternateColorCodes('&', msg);
    }
}
