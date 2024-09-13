package com.monkey.giveaway.Commands.GiveawayCommands;

import com.monkey.giveaway.Commands.Action.CommandAction;
import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.command.CommandSender;

public class StartCommandAction implements CommandAction {
    private static final String PERMISSION = "giveaway.start";
    private final GiveawayManager giveawayManager;

    public StartCommandAction(GiveawayManager giveawayManager) {
        this.giveawayManager = giveawayManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di eseguire questo comando!");
            return false;
        }

        if (!giveawayManager.isGiveawayEnabled()) {
            giveawayManager.startGiveaway();
            ChatUtils.broadcastMessage("Giveaway iniziato!");
        } else {
            ChatUtils.sendMessageError(sender, "C'è già un giveaway in corso!");
        }
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(PERMISSION);
    }
}
