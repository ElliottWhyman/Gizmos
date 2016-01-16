package me.itselliott.gizmos.gizmo.gizmos.downpour;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import me.itselliott.gizmos.utils.constants.StringConstants;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import static me.itselliott.gizmos.utils.constants.GizmoConstants.DOWNPOUR;

/**
 * Created by Elliott2 on 14/01/2016.
 */
public class DownpourGizmo extends Gizmo {

    private int radius;

    public DownpourGizmo() {
        super(DOWNPOUR, new ItemBuilder(Material.GHAST_TEAR).setName(ChatColor.AQUA + DOWNPOUR.name()).createItem());
        this.radius = 3;
        Gizmos.get().registerListener(this);
    }

    @EventHandler
    public void onGizmoUse(GizmoUseEvent event) {
        if (!event.isCancelled() && event.getGizmo() == this) {
            this.playDownpour(event.getLocation(), event.getPlayer());
        }
    }

    private void playDownpour(Location location, Player gizmoPlayer) {
        for (Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().distanceSquared(location) <= Math.pow(10, 2)) {
                Bukkit.broadcastMessage(player.getDisplayName());
                // Plays a sound to nearby players
                player.playSound(location, Sound.AMBIENCE_THUNDER, 1, 0);
                player.playSound(location, Sound.AMBIENCE_RAIN, 1, 0);
            }
        }
        this.dropRaindrops(location);
        GizmoUtil.remove(gizmoPlayer);
    }

    private void dropRaindrops(Location location) {
        for (int x = location.getBlockX() - this.radius; x <= location.getBlockX() + this.radius; x++) {
            for (int y = (location.getBlockY() + 5) - this.radius; y <= (location.getBlockY() + 5) + this.radius; y++) {
                for (int z = location.getBlockZ() - this.radius; z <= location.getBlockZ() + this.radius; z++) {
                    location.getWorld().dropItem(new Location(location.getWorld(), x, y, z), new ItemBuilder(Material.GHAST_TEAR).setName(StringConstants.RAINDROPS.name()).createItem());
                }
            }
        }
    }

    @Override
    public void clickAction(Menu menu, Player player) {
        GizmoUtil.checkAndBuyGizmo(player, this, this.getItem());
    }

}
