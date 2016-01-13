package me.itselliott.gizmos.gizmo;

import me.itselliott.gizmos.event.raindrop.RaindropEvent;
import me.itselliott.gizmos.gizmo.gizmos.paintGun.Paint;
import me.itselliott.gizmos.gizmo.gizmos.paintGun.PaintGizmo;
import me.itselliott.gizmos.gizmo.gizmos.raindropBomb.RaindropBombGizmo;
import me.itselliott.gizmos.gizmo.gizmos.snowball.SnowballGizmo;
import org.bukkit.Bukkit;

import java.util.*;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoRegistry {

    private Set<Class<? extends Gizmo>> gizmosClasses;
    private Set<Gizmo> gizmos;

    public GizmoRegistry() {
        this.gizmos = new HashSet<>();
        this.gizmosClasses = new HashSet<>();
        this.gizmosClasses.addAll(Arrays.asList(
                RaindropBombGizmo.class,
                PaintGizmo.class,
                SnowballGizmo.class
        ));
    }

    public void registerLoaders() {
        for (Class<? extends Gizmo> gizmo : this.gizmosClasses) {
            try {
                Gizmo gizmoInstance = gizmo.newInstance();
                gizmoInstance.registerListener();
                this.gizmos.add(gizmoInstance);
                Bukkit.getLogger().info("Gizmo Registered " + gizmoInstance.getName());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Set<Gizmo> getGizmos() {
        return this.gizmos;
    }

}