package me.itselliott.gizmos.event;

import me.itselliott.gizmos.gizmo.Gizmo;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Elliott on 13/12/2015.
 */
public class GizmoUseEvent extends GizmoEvent {

    private Location location;

    public GizmoUseEvent(Player player, Gizmo gizmo, Location location) {
        super(player, gizmo);
        this.location = location;
    }

    public Location getLocation() {
        return this.location;
    }

}
