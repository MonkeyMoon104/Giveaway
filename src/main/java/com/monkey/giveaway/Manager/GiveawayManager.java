package com.monkey.giveaway.Manager;

import com.monkey.giveaway.Giveaway;
import com.monkey.giveaway.Manager.UtilManager.BossBarManager;
import com.monkey.giveaway.Manager.UtilManager.ParticipantManager;
import com.monkey.giveaway.Manager.UtilManager.PrizeDistributor;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class GiveawayManager {
    private final Giveaway plugin;
    private final ParticipantManager participantManager;
    private final BossBarManager bossBarManager;
    private final PrizeDistributor prizeDistributor;
    private BukkitTask giveawayTask;
    private long giveawayEndTime;
    private long giveawayDurationMillis;
    private boolean giveawayEnabled;

    public GiveawayManager(Giveaway plugin) {
        this.plugin = plugin;
        this.participantManager = new ParticipantManager(this);
        this.bossBarManager = new BossBarManager();
        this.prizeDistributor = new PrizeDistributor();
        this.giveawayDurationMillis = plugin.getConfig().getLong("giveaway-duration") * 1000;
        this.giveawayEnabled = false;
    }

    public void startGiveaway() {
        if (giveawayEnabled) {
            ChatUtils.sendMessageError(Bukkit.getConsoleSender(), "Un giveaway è già in corso");
            return;
        }

        participantManager.clearParticipants();
        giveawayEndTime = System.currentTimeMillis() + giveawayDurationMillis;
        bossBarManager.createBossBar(giveawayDurationMillis);

        giveawayEnabled = true;

        giveawayTask = new BukkitRunnable() {
            @Override
            public void run() {
                long timeLeft = giveawayEndTime - System.currentTimeMillis();
                if (timeLeft <= 0) {
                    Bukkit.getLogger().info("Il giveaway è terminato");
                    finishGiveaway();
                    return;
                }

                bossBarManager.updateBossBar(timeLeft, giveawayDurationMillis);
            }
        }.runTaskTimer(plugin, 20L, 20L);

        announceGiveaway();
        ChatUtils.broadcastMessage("Il giveaway durerà " + (giveawayDurationMillis / 1000) + " secondi");
    }

    public void finishGiveaway() {
        if (!giveawayEnabled) {
            return;
        }

        if (giveawayTask != null) {
            giveawayTask.cancel();
            giveawayTask = null;
        }

        bossBarManager.removeBossBar();
        giveawayEnabled = false;

        drawWinner();
    }

    private void drawWinner() {
        Player winner = participantManager.selectRandomWinner();
        if (winner != null) {
            ChatUtils.broadcastMessage("Il vincitore del giveaway è: " + winner.getName() + "!");
            prizeDistributor.distributePrize(winner);
        } else {
            ChatUtils.broadcastMessage("Nessun partecipante nel giveaway");
        }

        participantManager.clearParticipants();
    }

    public void announceGiveaway() {
        ChatUtils.broadcastMessage("Un giveaway è iniziato, esegui il comando in chat /giveaway join per partecipare");
    }

    public boolean isGiveawayEnabled() {
        return giveawayEnabled;
    }

    public void setGiveawayDisabled() {
        finishGiveaway();
    }

    public ParticipantManager getParticipantManager() {
        return participantManager;
    }

    public BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    public void updateGiveawayDuration(long durationMillis) {
        this.giveawayDurationMillis = durationMillis;
        Bukkit.getLogger().info("Durata del giveaway aggiornata a: " + durationMillis / 1000 + " secondi");
    }

    public long getGiveawayEndTime() {
        return giveawayEndTime;
    }
}
