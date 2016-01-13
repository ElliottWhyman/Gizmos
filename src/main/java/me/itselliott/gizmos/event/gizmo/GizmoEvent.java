package me.itselliott.gizmos.event.gizmo;

import me.itselliott.gizmos.gizmo.Gizmo;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public class GizmoEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Gizmo gizmo;
    private boolean canceled;

    public GizmoEvent(Player player, Gizmo gizmo) {
        this.player = player;
        this.gizmo = gizmo;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Gizmo getGizmo() {
        return this.gizmo;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return canceled;
    }

    @Override
    public void setCancelled(boolean canceled) {
        this.canceled = canceled;
    }
}
