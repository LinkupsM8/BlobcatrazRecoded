package com.ToonBasic.blobcatraz.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.ToonBasic.blobcatraz.utility.Util;

public abstract class ICommand implements CommandExecutor {
    private String command;
    private String usage;
    private Permission perm;
    private String[] aliases;
    private int minArgs = 0;
    private boolean player = false, disabled = false;
    private CommandSender sender;
    private String commandUsed;

    public String prefix = Util.prefix;
    public ICommand(String name, String usage) {this(name, usage, null);}
    public ICommand(String name) {this(name, "", null);}
    public ICommand(String name, String usage, String perm, String... aliases) {
        this.command = name;
        this.usage = usage;
        this.aliases = aliases;
        this.perm = new Permission(perm);
        if ((usage != null) && (!usage.equalsIgnoreCase(""))) {
            Matcher matcher = Pattern.compile("<.*?>").matcher(usage);
            while (matcher.find()) {this.minArgs += 1;}
        }
        Class<? extends ICommand> clazz = getClass();
        if(clazz.isAnnotationPresent(PlayerOnly.class)) this.player = true;
        if(clazz.isAnnotationPresent(Disabled.class)) this.disabled = true;
    }

    public boolean onCommand(CommandSender cs, Command c, String label, String[] args) {return executeCmd(cs, c, label, args);}

    private boolean executeCmd(CommandSender sender, Command cmd, String label, String[] args) {
        this.sender = sender;
        this.commandUsed = label;
        if(disabled) {
            sender.sendMessage(Language.COMMAND_DISABLED);
            return true;
        }
        if(player) {
            boolean p = (sender instanceof Player);
            if(!p) {
                sender.sendMessage(Language.PLAYER_ONLY);
                return true;
            }
        }
        if(sender instanceof Player) {
            boolean has = sender.hasPermission(perm);
            if(!has) {
                sender.sendMessage(Util.color(Language.NO_PERMISSION));
                return true;
            }
        }
        if(args.length < minArgs) {
            sender.sendMessage(getFormattedUsage(label));
            return true;
        }
        try {
            handleCommand(sender, args);
        } catch(Throwable ex) {
            String error = Util.color("There was a command processing error! Please report this!");
            sender.sendMessage(error);
            ex.printStackTrace();
        }
        return true;
    }

    public String getFormattedUsage(String command) {
    	String format = String.format(Language.INCORRECT_USAGE, (command + " " + usage));
    	String usage = Util.color(format);
        return usage;
    }

    public CommandExecutor getExecutor() {return this;}
    public abstract void handleCommand(CommandSender cs, String[] args);
    public String getCommand() {return command;}
    public String getUsage() {return usage;}
    public CommandSender getSender() {return sender;}
    public String[] getAliases() {return aliases;}
    public String getCommandUsed() {return commandUsed;}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface PlayerOnly {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    public @interface Disabled {}

    public static class Language {
        public static String NO_PERMISSION = Util.color("&cYou're not allowed to use this command!");
        public static String PLAYER_ONLY = Util.color("&cOnly players can execute this command");
        public static String INVALID_TARGET = Util.color("&cInvalid Target");
        public static String INCORRECT_USAGE = Util.color("&cIncorrect usage, please try again with these arguments:\n&f/%1s");
        public static String COMMAND_DISABLED = Util.color("&cThis command is disabled");
    }
}