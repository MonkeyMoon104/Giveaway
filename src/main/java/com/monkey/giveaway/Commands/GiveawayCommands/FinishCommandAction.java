package com.monkey.giveaway.Commands.GiveawayCommands;

import com.monkey.giveaway.Commands.Action.CommandAction;
import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.command.CommandSender;

public class FinishCommandAction implements CommandAction {
    private static final String PERMISSION = "giveaway.finish";
    private final GiveawayManager giveawayManager;

    public FinishCommandAction(GiveawayManager giveawayManager) {
        this.giveawayManager = giveawayManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di eseguire questo comando!");
            return false;
        }

        if (giveawayManager.isGiveawayEnabled()) {
            giveawayManager.finishGiveaway();
        } else {
            ChatUtils.sendMessageError(sender, "Non c'è nessun giveaway attivo!");
        }
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(PERMISSION);
    }
}
