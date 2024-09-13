package com.monkey.giveaway.Commands.Action;

import org.bukkit.command.CommandSender;

public interface CommandAction {
    boolean execute(CommandSender sender, String[] args);

    default boolean hasPermission(CommandSender sender) {
        return true;
    }
}
