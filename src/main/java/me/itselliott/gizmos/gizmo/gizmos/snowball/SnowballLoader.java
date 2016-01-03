package me.itselliott.gizmos.gizmo.gizmos.snowball;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.gizmo.GizmoLoader;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.GizmoUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public class SnowballLoader implements GizmoLoader, Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getItem().hasItemMeta() && ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals(Constants.SNOWBALL_GIZMO)) {
            if (!GizmoUtil.hasGizmos(event.getPlayer())) {
                Gizmo gizmo = new SnowballGizmo();
                // Registers events inside of the gizmo class
                Gizmos.get().registerListener(gizmo);
                // Put player using the gizmo in global map
                GizmoUtil.playerGizmos.put(event.getPlayer().getName(), gizmo);
                Bukkit.getPluginManager().callEvent(new GizmoUseEvent(event.getPlayer(), gizmo, event.getPlayer().getLocation()));
                event.setCancelled(true);
            }
        }
    }

    @Override
    public void registerListener() {
        Gizmos.get().registerListener(this);
    }

    @Override
    public Gizmo getGizmo() {
        return new SnowballGizmo();
    }
}
