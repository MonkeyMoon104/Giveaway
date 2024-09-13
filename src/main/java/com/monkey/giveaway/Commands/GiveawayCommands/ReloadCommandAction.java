package com.monkey.giveaway.Commands.GiveawayCommands;

import com.monkey.giveaway.Commands.Action.CommandAction;
import com.monkey.giveaway.Giveaway;
import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.command.CommandSender;

public class ReloadCommandAction implements CommandAction {
    private static final String PERMISSION = "giveaway.reload";
    private final Giveaway plugin;
    private final GiveawayManager giveawayManager;

    public ReloadCommandAction(Giveaway plugin, GiveawayManager giveawayManager) {
        this.plugin = plugin;
        this.giveawayManager = giveawayManager;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            ChatUtils.sendMessageError(sender, "Non hai il permesso di eseguire questo comando!");
            return false;
        }

        plugin.reloadConfig();
        giveawayManager.updateGiveawayDuration(plugin.getConfig().getLong("giveaway-duration") * 1000);
        ChatUtils.broadcastMessage("Config ricaricata!");
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(PERMISSION);
    }
}
