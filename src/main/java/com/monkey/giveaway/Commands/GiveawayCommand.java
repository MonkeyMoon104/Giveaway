package com.monkey.giveaway.Commands;

import com.monkey.giveaway.Giveaway;
import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveawayCommand implements CommandExecutor {
    private final GiveawayManager giveawayManager;
    private final Giveaway plugin;

    public GiveawayCommand(Giveaway plugin, GiveawayManager giveawayManager) {
        this.plugin = plugin;
        this.giveawayManager = giveawayManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            ChatUtils.sendMessageError(sender, "Utilizza: /giveaway <start|join|finish|reload>");
            return false;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "start":
                return handleStart(sender);
            case "join":
                return handleJoin(sender);
            case "finish":
                return handleFinish(sender);
            case "reload":
                return handleReload(sender);
            default:
                ChatUtils.sendMessageError(sender, "Comando sconosciuto. Usa: /giveaway <start|join|finish|reload>");
                return false;
        }
    }

    private boolean handleStart(CommandSender sender) {
        if (sender.hasPermission("giveaway.start")) {
            if (!giveawayManager.isGiveawayEnabled()) {
                giveawayManager.startGiveaway();
                ChatUtils.sendMessage(sender, "Giveaway iniziato!");
            } else {
                ChatUtils.sendMessageError(sender, "C'è già un giveaway in corso!");
            }
        } else {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di eseguire questo comando!");
        }
        return true;
    }

    private boolean handleJoin(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("giveaway.join")) {
                if (giveawayManager.isGiveawayEnabled()) {
                    giveawayManager.addParticipant(player);
                } else {
                    ChatUtils.sendMessageError(sender, "Non c'è nessun giveaway attivo!");
                }
            } else {
                ChatUtils.sendMessageError(sender, "Non hai il permesso di partecipare a questo giveaway!");
            }
        } else {
            ChatUtils.sendMessageError(sender, "Solo i player possono entrare nel giveaway!");
        }
        return true;
    }

    private boolean handleFinish(CommandSender sender) {
        if (sender.hasPermission("giveaway.finish")) {
            if (giveawayManager.isGiveawayEnabled()) {
                giveawayManager.finishGiveaway();
            } else {
                ChatUtils.sendMessageError(sender, "Non c'è nessun giveaway attivo!");
            }
        } else {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di eseguire questo comando!");
        }
        return true;
    }

    private boolean handleReload(CommandSender sender) {
        if (sender.hasPermission("giveaway.reload")) {
            plugin.reloadConfig();
            plugin.getConfig().options().copyDefaults(true);
            giveawayManager.updateGiveawayDuration(plugin.getConfig().getLong("giveaway-duration") * 1000);
            ChatUtils.sendMessage(sender, "Config ricaricata!");
        } else {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di eseguire questo comando!");
        }
        return true;
    }
}
