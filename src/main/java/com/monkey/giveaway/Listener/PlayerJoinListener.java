package com.monkey.giveaway.Listener;

import com.monkey.giveaway.Manager.GiveawayManager;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class PlayerJoinListener implements Listener {
    private final GiveawayManager giveawayManager;

    public PlayerJoinListener(GiveawayManager giveawayManager) {
        this.giveawayManager = giveawayManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (giveawayManager.getParticipantManager().isParticipant(player)) {
            giveawayManager.getBossBarManager().addPlayerToBossBar(player);
        } else {
            long timeLeft = giveawayManager.getGiveawayEndTime() - System.currentTimeMillis();
            if (timeLeft > 0) {
                long secondsLeft = timeLeft / 1000;
                ChatUtils.sendMessage(player, "Un giveaway è in corso, utilizza /gw join per partecipare, mancano " + secondsLeft + " secondi");
            }
        }
    }
}
