package com.monkey.giveaway.Utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtils {
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.GREEN + message);
    }

    public static void sendMessageError(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + message);
    }

    public static void broadcastMessage(String message) {
        org.bukkit.Bukkit.broadcastMessage(ChatColor.AQUA + message);
    }
}