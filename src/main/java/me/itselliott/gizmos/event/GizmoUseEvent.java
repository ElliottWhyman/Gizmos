package me.itselliott.gizmos.event;

import me.itselliott.gizmos.gizmo.Gizmo;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Elliott on 13/12/2015.
 */
public class GizmoUseEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private Gizmo gizmo;
    private Location location;
    private boolean canceled;

    public GizmoUseEvent(Player player, Gizmo gizmo, Location location) {
        this.player = player;
        this.gizmo = gizmo;
        this.location = location;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Gizmo getGizmo() {
        return this.gizmo;
    }

    public Location getLocation() {
        return this.location;
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
