package com.monkey.giveaway.Listener;

import com.monkey.giveaway.Manager.GiveawayManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;

public class PlayerQuitListener implements Listener {
    private final GiveawayManager giveawayManager;

    public PlayerQuitListener(GiveawayManager giveawayManager) {
        this.giveawayManager = giveawayManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        giveawayManager.getBossBarManager().removePlayerFromBossBar(player);
        giveawayManager.getParticipantManager().removeParticipant(player);
    }
}
