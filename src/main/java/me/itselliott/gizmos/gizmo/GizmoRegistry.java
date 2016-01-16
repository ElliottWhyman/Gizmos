package me.itselliott.gizmos.gizmo;

import me.itselliott.gizmos.gizmo.gizmos.animalGun.AnimalGunGizmo;
import me.itselliott.gizmos.gizmo.gizmos.downpour.DownpourGizmo;
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
    private List<Gizmo> gizmos;

    public GizmoRegistry() {
        this.gizmos = new ArrayList<>();
        this.gizmosClasses = new HashSet<>();
        this.gizmosClasses.addAll(Arrays.asList(
                RaindropBombGizmo.class,
                PaintGizmo.class,
                SnowballGizmo.class,
                DownpourGizmo.class,
                AnimalGunGizmo.class
        ));
    }

    public void registerLoaders() {
        for (Class<? extends Gizmo> gizmo : this.gizmosClasses) {
            try {
                Gizmo gizmoInstance = gizmo.newInstance();
                this.gizmos.add(gizmoInstance);
                Bukkit.getLogger().info("Gizmo Registered " + gizmoInstance.getName());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Gizmo> getGizmos() {
        return this.gizmos;
    }

}