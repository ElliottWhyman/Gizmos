package me.itselliott.gizmos.raindrops;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.RaindropReceiveEvent;
import me.itselliott.gizmos.event.RaindropUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Elliott on 05/12/2015.
 */
public class RaindropHandler {

    private Gizmos plugin;
    private HashMap<UUID, RaindropScoreboard> raindropScoreboards;

    public RaindropHandler(Gizmos plugin) {
        this.plugin = plugin;
        this.raindropScoreboards = new HashMap<>();
    }

    private HashMap<UUID, Integer> raindrops; // Where the amount of raindrops each player is stored

    public RaindropHandler() {
        this.raindrops = new HashMap<>();
    }

    public HashMap<UUID, Integer> getRaindrops() {
        return raindrops;
    }

    public int getRaindrops(UUID player) {
        return this.raindrops.get(player);
    }

    public void setRaindrops(UUID player, int value) {
        this.raindrops.put(player, value);
    }

    public boolean canAfford(UUID player, int value) {
        return this.raindrops.get(player) - value >= 0;
    }

    public HashMap<UUID, RaindropScoreboard> getRaindropScoreboards() {
        return this.raindropScoreboards;
    }

    @EventHandler
    public void onRaindropRecieve(RaindropReceiveEvent event) {
        event.getPlayer().sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "+" + event.getAmount() + ChatColor.RESET + "" + ChatColor.DARK_PURPLE + " | " + ChatColor.WHITE + "Raindrops Received");
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 0);

        Bukkit.getPluginManager().callEvent(new RaindropUpdateEvent(event.getPlayer()));
    }

    @EventHandler
    public void onRaindropUpdate(RaindropUpdateEvent event) {
        // Updates the scoreboard to show new raindrop count
        this.raindropScoreboards.get(event.getPlayer().getUniqueId()).update();
    }

}
