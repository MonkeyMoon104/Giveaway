package com.monkey.giveaway.Commands.GiveawayCommands;

import com.monkey.giveaway.Commands.Action.CommandAction;
import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommandAction implements CommandAction {
    private static final String PERMISSION = "giveaway.join";
    private final GiveawayManager giveawayManager;

    public JoinCommandAction(GiveawayManager giveawayManager) {
        this.giveawayManager = giveawayManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di partecipare a questo giveaway!");
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (giveawayManager.isGiveawayEnabled()) {
                giveawayManager.getParticipantManager().addParticipant(player);
            } else {
                ChatUtils.sendMessageError(sender, "Non c'è nessun giveaway attivo!");
            }
        } else {
            ChatUtils.sendMessageError(sender, "Solo i player possono entrare nel giveaway!");
        }
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(PERMISSION);
    }
}
