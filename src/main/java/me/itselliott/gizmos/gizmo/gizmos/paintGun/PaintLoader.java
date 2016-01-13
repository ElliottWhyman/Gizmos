package me.itselliott.gizmos.gizmo.gizmos.paintGun;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.gizmo.GizmoLoader;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import me.itselliott.gizmos.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Elliott2 on 30/12/2015.
 */
public class PaintLoader implements GizmoLoader, Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getItem().hasItemMeta() && StringUtil.checkStrings(event.getItem().getItemMeta().getDisplayName(), Constants.PAINT_GIZMO)) {
            if (!GizmoUtil.hasGizmos(event.getPlayer())) {
                Gizmo gizmo = new PaintGizmo(event.getItem());
                // Registers events inside of the gizmo class
                Gizmos.get().registerListener(gizmo);
                // Put player using the gizmo in global map
                GizmoUtil.playerGizmos.put(event.getPlayer().getName(), gizmo);
                // Calls the event that triggers the gizmo
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
        return new PaintGizmo(new ItemBuilder(Material.STAINED_CLAY).createItem());
    }
}
