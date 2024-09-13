package com.monkey.giveaway.Manager.UtilManager;

import com.monkey.giveaway.Utils.ChatUtils;
import com.monkey.giveaway.Manager.GiveawayManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ParticipantManager {
    private final GiveawayManager giveawayManager;
    private final Set<Player> participants = new HashSet<>();

    public ParticipantManager(GiveawayManager giveawayManager) {
        this.giveawayManager = giveawayManager;
    }

    public void addParticipant(Player player) {
        if (!giveawayManager.isGiveawayEnabled()) {
            ChatUtils.sendMessageError(player, "Nessun giveaway è in corso");
            return;
        }

        if (participants.add(player)) {
            ChatUtils.sendMessage(player, "Sei entrato a far parte del giveaway");
            Bukkit.getLogger().info("Giocatore " + player.getName() + " aggiunto al giveaway");
            giveawayManager.getBossBarManager().addPlayerToBossBar(player);
        } else {
            ChatUtils.sendMessage(player, "Sei già un partecipante di questo giveaway");
        }
    }

    public void removeParticipant(Player player) {
        if (participants.remove(player)) {
            Bukkit.getLogger().info("Giocatore " + player.getName() + " rimosso dai partecipanti");
            giveawayManager.getBossBarManager().removePlayerFromBossBar(player);
        }
    }

    public void clearParticipants() {
        participants.clear();
    }

    public Player selectRandomWinner() {
        if (participants.isEmpty()) {
            return null;
        }

        return participants.stream()
                .skip(ThreadLocalRandom.current().nextInt(participants.size()))
                .findFirst()
                .orElse(null);
    }

    public boolean isParticipant(Player player) {
        return participants.contains(player);
    }
}
