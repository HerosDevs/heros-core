package net.herospvp.heroscore.coins.listeners;

import net.herospvp.database.lib.Musician;
import net.herospvp.heroscore.HerosCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ConnectionListeners implements Listener {

    private final HerosCore plugin;
    private final Musician musician;

    public ConnectionListeners(HerosCore plugin) {
        this.plugin = plugin;
        this.musician = plugin.getMusician();
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        musician.offer(plugin.getPlayersHandler().load(uuid, () -> {}));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        musician.offer(plugin.getPlayersHandler().save(uuid, () -> {
            synchronized (plugin.getPlayersHandler().getPlayers()) {
                plugin.getPlayersHandler().getPlayers().remove(uuid);
            }
        }));
    }

}
