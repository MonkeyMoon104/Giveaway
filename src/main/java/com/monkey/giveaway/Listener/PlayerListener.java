package com.monkey.giveaway.Listener;

import com.monkey.giveaway.Manager.GiveawayManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class PlayerListener implements Listener {
    private final GiveawayManager giveawayManager;

    public PlayerListener(GiveawayManager giveawayManager) {
        this.giveawayManager = giveawayManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (giveawayManager.isGiveawayEnabled()) {
            BossBar bossBar = giveawayManager.getBossBar();
            if (bossBar != null) {
                bossBar.addPlayer(player);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (giveawayManager.isGiveawayEnabled()) {
            BossBar bossBar = giveawayManager.getBossBar();
            if (bossBar != null) {
                bossBar.removePlayer(player);
            }

            if (giveawayManager.isParticipant(player)) {
                giveawayManager.removeParticipant(player);
            }
        }
    }
}
