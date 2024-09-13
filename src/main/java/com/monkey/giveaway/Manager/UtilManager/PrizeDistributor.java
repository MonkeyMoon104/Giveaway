package com.monkey.giveaway.Manager.UtilManager;

import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PrizeDistributor {
    public void distributePrize(Player winner) {
        if (winner == null) {
            ChatUtils.broadcastMessage("Nessun vincitore da premiare");
            return;
        }

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "give " + winner.getName() + " minecraft:diamond 1");
        ChatUtils.sendMessage(winner, "Congratulazioni, hai vinto un diamante!");
    }
}
