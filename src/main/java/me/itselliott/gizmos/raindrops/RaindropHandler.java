package me.itselliott.gizmos.raindrops;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.raindrop.RaindropUpdateEvent;
import me.itselliott.gizmos.event.raindrop.RaindropEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Elliott on 05/12/2015.
 */
public class RaindropHandler implements Listener {

    private Gizmos plugin;
    private HashMap<UUID, RaindropScoreboard> raindropScoreboards;
    private HashMap<UUID, Integer> raindrops; // Where the amount of raindrops each player is stored

    public RaindropHandler(Gizmos plugin) {
        this.plugin = plugin;
        this.raindropScoreboards = new HashMap<>();
        this.raindrops = new HashMap<>();
        this.plugin.registerListener(this);
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
    public void onRaindropRecieve(RaindropUpdateEvent event) {
        if (event.getAmount() > 0) {
            event.getPlayer().sendMessage(ChatColor.AQUA + "" + event.getAmount() + ChatColor.RESET + "" + ChatColor.DARK_PURPLE + " | " + ChatColor.WHITE + "Raindrops Received");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 0);
        } else if (event.getAmount() < 0) {
            event.getPlayer().sendMessage(ChatColor.AQUA + "" + event.getAmount() + ChatColor.RESET + "" + ChatColor.DARK_PURPLE + " | " + ChatColor.RED + "Raindrops Removed");
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ANVIL_LAND, 1, 0);
        }
        // Updates the scoreboard to show new raindrop count
        this.raindropScoreboards.get(event.getPlayer().getUniqueId()).update();
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (event.getItem().getItemStack().getType().equals(Material.GHAST_TEAR)) {
            // Gives player raindrops on pickup
            Gizmos.get().getRaindropHandler().setRaindrops(event.getPlayer().getUniqueId(), Gizmos.get().getRaindropHandler().getRaindrops(event.getPlayer().getUniqueId()) + 1);
            // Call raindrop event
            RaindropUpdateEvent raindropReceiveEvent = new RaindropUpdateEvent(event.getPlayer(), 10);
            Bukkit.getPluginManager().callEvent(raindropReceiveEvent);

            // Remove item from world
            event.setCancelled(true);
            event.getItem().remove();
        }
    }
}
