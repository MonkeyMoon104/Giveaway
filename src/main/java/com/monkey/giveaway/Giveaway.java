package com.monkey.giveaway;

import com.monkey.giveaway.Commands.CommandManager.GiveawayCommandManager;
import com.monkey.giveaway.Completers.TAB.GiveawayTabCompleter;
import com.monkey.giveaway.Listener.PlayerJoinListener;
import com.monkey.giveaway.Listener.PlayerQuitListener;
import com.monkey.giveaway.Manager.GiveawayManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Giveaway extends JavaPlugin {

    private GiveawayManager giveawayManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.giveawayManager = new GiveawayManager(this);
        getCommand("giveaway").setExecutor(new GiveawayCommandManager(this, giveawayManager));
        getCommand("giveaway").setTabCompleter(new GiveawayTabCompleter());
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(giveawayManager), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(giveawayManager), this);
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
