package com.ToonBasic.blobcatraz.config;

import com.ToonBasic.blobcatraz.Core;
import com.ToonBasic.blobcatraz.PublicHandlers;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class ConfigDatabase {
    private static final File folder = new File(Core.folder, "users");

    public static YamlConfiguration load(OfflinePlayer op) {
        try {
            UUID uuid = op.getUniqueId();
            String f = uuid + ".yml";
            File file = new File(folder, f);
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            if(!file.exists()) save(config, file);
            config.load(file);
            defaults(op, config);
            return config;
        } catch(Exception ex) {
            String error = "Failed to load data for " + op.getName() + ":\n" + ex.getMessage();
            PublicHandlers.print(error);
            return null;
        }
    }

    public static void save(YamlConfiguration config, File file) {
        try {
            if(!file.exists()) {
                folder.mkdirs();
                file.createNewFile();
            }
            config.save(file);
        } catch(Exception ex) {
            String error = "Failed to save data in " + file + ":\n" + ex.getMessage();
            PublicHandlers.print(error);
        }
    }

    private static void defaults(OfflinePlayer op, YamlConfiguration config) {
        UUID uuid = op.getUniqueId();
        File file = new File(folder, uuid + ".yml");

        set(config, "username", op.getName(), false);
        set(config, "nickname", op.getName(), false);
        set(config, "balance", 0.00D, false);
        set(config, "votes.amount", 0, false);
        set(config, "votes.next prize", 5, false);
        save(config, file);
    }

    private static void set(YamlConfiguration config, String path, Object value, boolean force) {
        boolean b = (config.get(path) == null);
        if(b || force) config.set(path, value);
    }
}