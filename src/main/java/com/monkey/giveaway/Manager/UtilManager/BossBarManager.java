package com.monkey.giveaway.Manager.UtilManager;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossBarManager {
    private BossBar bossBar;

    public void createBossBar(long giveawayDurationMillis) {
        bossBar = Bukkit.createBossBar("§9Giveaway: " + (giveawayDurationMillis / 1000) + " seconds", BarColor.BLUE, BarStyle.SOLID);
    }

    public void updateBossBar(long timeLeft, long giveawayDurationMillis) {
        if (bossBar != null) {
            bossBar.setTitle("§9Giveaway: " + (timeLeft / 1000) + " seconds");
            bossBar.setProgress((double) timeLeft / giveawayDurationMillis);
        }
    }

    public void addPlayerToBossBar(Player player) {
        if (bossBar != null && !bossBar.getPlayers().contains(player)) {
            bossBar.addPlayer(player);
        }
    }

    public void removePlayerFromBossBar(Player player) {
        if (bossBar != null && bossBar.getPlayers().contains(player)) {
            bossBar.removePlayer(player);
        }
    }

    public void removeBossBar() {
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar = null;
        }
    }
}
