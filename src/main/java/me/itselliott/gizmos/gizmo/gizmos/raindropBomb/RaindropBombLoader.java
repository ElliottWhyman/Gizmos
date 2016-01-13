package me.itselliott.gizmos.gizmo.gizmos.raindropBomb;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.gizmo.GizmoLoader;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Elliott2 on 30/12/2015.
 */
public class RaindropBombLoader implements GizmoLoader, Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem().hasItemMeta() && StringUtil.checkStrings(event.getItem().getItemMeta().getDisplayName(), Constants.RAINDROP_BOMB)) {
            if (!GizmoUtil.hasGizmos(event.getPlayer())) {
                Gizmo gizmo = new RaindropBombGizmo();
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
        return new RaindropBombGizmo();
    }
}
