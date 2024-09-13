package com.monkey.giveaway.Commands.CommandManager;

import com.monkey.giveaway.Commands.Action.CommandAction;
import com.monkey.giveaway.Commands.GiveawayCommands.*;
import com.monkey.giveaway.Giveaway;
import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class GiveawayCommandManager implements CommandExecutor {
    private final Map<String, CommandAction> commandActions = new HashMap<>();

    public GiveawayCommandManager(Giveaway plugin, GiveawayManager giveawayManager) {
        commandActions.put("start", new StartCommandAction(giveawayManager));
        commandActions.put("join", new JoinCommandAction(giveawayManager));
        commandActions.put("finish", new FinishCommandAction(giveawayManager));
        commandActions.put("reload", new ReloadCommandAction(plugin, giveawayManager));
        commandActions.put("leave", new LeaveCommandAction(plugin, giveawayManager));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return sendErrorMessage(sender, "Utilizza: /giveaway <start/join/leave/finish/reload>");
        }

        CommandAction action = commandActions.get(args[0].toLowerCase());
        if (action != null) {
            return action.execute(sender, args);
        } else {
            return sendErrorMessage(sender, "Comando sconosciuto! Usa: /giveaway <start/join/leave/finish/reload>");
        }
    }

    private boolean sendErrorMessage(CommandSender sender, String message) {
        ChatUtils.sendMessageError(sender, message);
        return false;
    }
}
