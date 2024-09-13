package com.monkey.giveaway.Commands.GiveawayCommands;

import com.monkey.giveaway.Commands.Action.CommandAction;
import com.monkey.giveaway.Giveaway;
import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommandAction implements CommandAction {
    private static final String PERMISSION = "giveaway.leave";
    private final GiveawayManager giveawayManager;
    private final Giveaway plugin;

    public LeaveCommandAction(Giveaway plugin, GiveawayManager giveawayManager) {
        this.plugin = plugin;
        this.giveawayManager = giveawayManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di eseguire questo comando!");
            return false;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (giveawayManager.isGiveawayEnabled()) {
                giveawayManager.getParticipantManager().removeParticipant(player);
                ChatUtils.sendMessage(player, "Sei uscito dal giveaway");
            } else {
                ChatUtils.sendMessageError(sender, "Non c'è nessun giveaway attivo!");
            }
        } else {
            ChatUtils.sendMessageError(sender, "Solo i giocatori possono uscire dal giveaway!");
        }
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(PERMISSION);
    }
}
