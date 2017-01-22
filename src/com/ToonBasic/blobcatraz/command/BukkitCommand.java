package com.ToonBasic.blobcatraz.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class BukkitCommand extends Command {
    private final Plugin owningPlugin;
    private CommandExecutor executor;

    protected BukkitCommand(String label, CommandExecutor executor, Plugin owner) {
        super(label);
        this.executor = executor;
        owningPlugin = owner;
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        boolean success;
        if (!owningPlugin.isEnabled()) {
            sender.sendMessage("§cThe owning plugin ('" + owningPlugin.getName() + "') for this command is not enabled!");
            return false;
        }
        try {
            success = executor.onCommand(sender, this, commandLabel, args);
        } catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + owningPlugin.getDescription().getFullName(), ex);
        }
        return success;
    }
}