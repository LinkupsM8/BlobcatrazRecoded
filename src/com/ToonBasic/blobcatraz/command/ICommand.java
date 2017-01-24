package com.ToonBasic.blobcatraz.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.ToonBasic.blobcatraz.PublicHandlers;

public abstract class ICommand implements CommandExecutor {
    private String command;
    private String usage;
    private Permission perm;
    private String[] aliases;
    private int minArgs;
    private boolean player = false, disabled = false;
    private CommandSender sender;
    private String commandUsed;

    public String prefix = PublicHandlers.prefix;
    public ICommand(String name, String usage) {
        this(name, usage, null);
    }

    public ICommand(String name) {
        this(name, "", null);
    }

    public ICommand(String name, String usage, String perm, String... aliases) {
        this.command = name;
        this.usage = usage;
        this.aliases = aliases;
        this.perm = new Permission(perm);
        if ((usage != null) && (!usage.equalsIgnoreCase(""))) {
            Matcher matcher = Pattern.compile("<.*?>").matcher(usage);
            while (matcher.find()) {
                this.minArgs += 1;
            }
        }
        if (getClass().getAnnotation(PlayerOnly.class) != null)
            this.player = true;
        if (getClass().getAnnotation(Disabled.class) != null)
            this.disabled = true;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return executeCmd(sender, cmd, label, args);
    }

    private boolean executeCmd(CommandSender sender, Command cmd, String label, String[] args) {
        this.sender = sender;
        commandUsed = label;
        if (disabled) {
            sender.sendMessage(Language.COMMAND_DISABLED);
            return true;
        }
        if ((player) && (!(sender instanceof Player))) {
            sender.sendMessage(Language.PLAYER_ONLY);
            return true;
        }
        if ((sender instanceof Player))
            if (!sender.hasPermission(perm)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Language.NO_PERMISSION));
                return true;
            }


        if (args.length < minArgs) {
            sender.sendMessage(getFormattedUsage(label));
            return true;
        }

        try {
            handleCommand(sender, args);
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "There was an error processing this command. Please report this!"));
            e.printStackTrace();
        }
        return true;
    }

    public String getFormattedUsage(String command) {
        return ChatColor.translateAlternateColorCodes('&', String.format(Language.INCORRECT_USAGE, (command + " " + usage)));
    }

    public CommandExecutor getExecutor() {
        return this;
    }

    public abstract void handleCommand(CommandSender sender, String[] args);

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return usage;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String getCommandUsed() {
        return commandUsed;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface PlayerOnly {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface Disabled {
    }

    public static class Language {
        public static String NO_PERMISSION = "&cYou're not allowed to use this command!";
        public static String PLAYER_ONLY = "Only players can execute this command";
        public static String INCORRECT_USAGE = "&7Usage: &e/{0}";
        public static String COMMAND_DISABLED = "&cThis command is disabled";
    }
}