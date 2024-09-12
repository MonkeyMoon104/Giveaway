package com.monkey.giveaway.Manager;

import com.monkey.giveaway.Giveaway;
import com.monkey.giveaway.Utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class GiveawayManager {
    private final Giveaway plugin;
    private final Set<Player> participants = new HashSet<>();
    private BukkitTask giveawayTask;
    private long giveawayEndTime;
    private long giveawayDurationMillis;
    private BossBar bossBar;
    private boolean giveawayEnabled;

    public GiveawayManager(Giveaway plugin) {
        this.plugin = plugin;
        this.giveawayDurationMillis = plugin.getConfig().getLong("giveaway-duration") * 1000;
        this.giveawayEnabled = false;
    }

    public void addParticipant(Player player) {
        if (giveawayTask == null || giveawayTask.isCancelled()) {
            ChatUtils.sendMessageError(player, "Nessun giveaway è in corso");
            return;
        }

        if (!participants.contains(player)) {
            participants.add(player);
            ChatUtils.sendMessage(player, "Sei entrato a far parte del giveaway");
            Bukkit.getLogger().info("Giocatore " + player.getName() + " aggiunto al giveaway");
        } else {
            ChatUtils.sendMessage(player, "Sei già un partecipante di questo giveaway");
            Bukkit.getLogger().info("Giocatore " + player.getName() + " già partecipante");
        }
    }

    public void removeParticipant(Player player) {
        if (participants.remove(player)) {
            Bukkit.getLogger().info("Giocatore " + player.getName() + " rimosso dai partecipanti");
        } else {
            Bukkit.getLogger().info("Giocatore " + player.getName() + " non trovato tra i partecipanti");
        }
    }

    public void startGiveaway() {
        if (giveawayEnabled) {
            ChatUtils.sendMessageError(Bukkit.getConsoleSender(), "Un giveaway è già in corso");
            return;
        }

        participants.clear();
        giveawayEndTime = System.currentTimeMillis() + giveawayDurationMillis;

        bossBar = Bukkit.createBossBar("§9Giveaway: " + (giveawayDurationMillis / 1000) + " seconds", BarColor.BLUE, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }

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

                bossBar.setTitle("§9Giveaway: " + (timeLeft / 1000) + " seconds");
                bossBar.setProgress((double) timeLeft / giveawayDurationMillis);
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

        if (bossBar != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                bossBar.removePlayer(player);
            }
            bossBar = null;
        }

        giveawayEnabled = false;

        drawWinner();
    }

    public void announceGiveaway() {
        ChatUtils.broadcastMessage("Un giveaway è iniziato, esegui il comando in chat /giveaway join per partecipare");
    }

    private void drawWinner() {
        if (participants.isEmpty()) {
            ChatUtils.broadcastMessage("Nessun partecipante nel giveaway");
            return;
        }

        Player winner = participants.stream()
                .skip(ThreadLocalRandom.current().nextInt(participants.size()))
                .findFirst()
                .orElse(null);

        if (winner != null) {
            ChatUtils.broadcastMessage("Il vincitore del giveaway è: " + winner.getName() + "!");
            distributePrize(winner);
        } else {
            ChatUtils.broadcastMessage("Errore nel determinare il vincitore.");
        }

        participants.clear();
    }

    public boolean isGiveawayEnabled() {
        return giveawayEnabled;
    }

    public boolean setGiveawayDisabled() {
        if (giveawayEnabled) {
            giveawayEnabled = false;

            if (giveawayTask != null) {
                giveawayTask.cancel();
                giveawayTask = null;
            }

            if (bossBar != null) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    bossBar.removePlayer(player);
                }
                bossBar = null;
            }

            participants.clear();
            return true;
        }
        return false;
    }

    public void updateGiveawayDuration(long durationMillis) {
        this.giveawayDurationMillis = durationMillis;
        Bukkit.getLogger().info("Durata del giveaway aggiornata a: " + durationMillis / 1000 + " secondi.");
    }

    private void distributePrize(Player winner) {
        if (winner == null) {
            ChatUtils.broadcastMessage("Nessun vincitore da premiare.");
            return;
        }

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "give " + winner.getName() + " minecraft:diamond 1");
        ChatUtils.sendMessage(winner, "Congratulazioni, hai vinto un diamante!");
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public boolean isParticipant(Player player) {
        return participants.contains(player);
    }

}
