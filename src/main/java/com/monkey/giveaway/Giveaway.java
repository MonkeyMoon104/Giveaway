package com.monkey.giveaway;

import com.monkey.giveaway.Commands.GiveawayCommand;
import com.monkey.giveaway.Completers.GiveawayTabCompleter;
import com.monkey.giveaway.Listener.PlayerListener;
import com.monkey.giveaway.Manager.GiveawayManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Giveaway extends JavaPlugin {

    private GiveawayManager giveawayManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.giveawayManager = new GiveawayManager(this);
        getCommand("giveaway").setExecutor(new GiveawayCommand(this, giveawayManager));
        getCommand("giveaway").setTabCompleter(new GiveawayTabCompleter());
        getServer().getPluginManager().registerEvents(new PlayerListener(giveawayManager), this);
        getServer().getConsoleSender().sendMessage("Giveaway Plugin abilitato!");
    }

    @Override
    public void onDisable() {
        if (giveawayManager != null) {
            giveawayManager.setGiveawayDisabled();
        }

        getServer().getConsoleSender().sendMessage("Giveaway Plugin disabilitato!");
    }

    public GiveawayManager getGiveawayManager() {
        return giveawayManager;
    }
}
