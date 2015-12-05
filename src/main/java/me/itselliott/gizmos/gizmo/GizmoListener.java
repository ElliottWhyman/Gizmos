package me.itselliott.gizmos.gizmo;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.utils.Constants;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Set;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoListener implements Listener {

    private Gizmos plugin;
    private Set<Gizmo> gizmos;

    public GizmoListener(Gizmos plugin) {
        this.plugin = plugin;
        this.gizmos = plugin.getRegistry().getGizmosSet();
        plugin.registerListener(this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        for (Gizmo gizmo : this.gizmos) {
            if (ChatColor.stripColor(event.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals(gizmo.getName())) {
                try {
                    Gizmo gizmo1 = gizmo.getClass().newInstance();
                    gizmo1.playGizmo(event.getPlayer().getLocation());
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                event.setCancelled(true);
            }
        }
    }
}
