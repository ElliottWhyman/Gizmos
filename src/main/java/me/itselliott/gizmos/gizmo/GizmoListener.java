package me.itselliott.gizmos.gizmo;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.GizmoUseEvent;
import me.itselliott.gizmos.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            for (Gizmo gizmo : this.gizmos) {
                if (event.getPlayer().getItemInHand().hasItemMeta()) {
                    if (ChatColor.stripColor(event.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals(gizmo.getName())) {
                        try {
                            gizmo.getClass().newInstance().registerListener();
                            Bukkit.getPluginManager().callEvent(new GizmoUseEvent(event.getPlayer(), gizmo, event.getPlayer().getLocation()));
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
