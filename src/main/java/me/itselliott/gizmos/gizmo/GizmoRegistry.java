package me.itselliott.gizmos.gizmo;

import me.itselliott.gizmos.gizmo.gizmos.paintGun.PaintLoader;
import me.itselliott.gizmos.gizmo.gizmos.raindropBomb.RaindropBombLoader;
import me.itselliott.gizmos.gizmo.gizmos.snowball.SnowballLoader;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

import java.util.*;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoRegistry {

    private final Map<String, Gizmo> gizmos;
    private Set<Class<? extends GizmoLoader>> loaderClasses;
    private Set<Gizmo> gizmoSet;

    public GizmoRegistry() {
        this.gizmos = new HashMap<>();
        this.gizmoSet = new HashSet<>();
        this.loaderClasses = new HashSet<>();
        this.loaderClasses.addAll(Arrays.asList(
                RaindropBombLoader.class,
                PaintLoader.class,
                SnowballLoader.class
        ));
    }

    public void registerLoaders() {
        for (Class<? extends GizmoLoader> loader : this.loaderClasses) {
            try {
                GizmoLoader gizmoLoader = loader.newInstance();
                gizmoLoader.registerListener();
                Gizmo gizmo = gizmoLoader.getGizmo();
                this.gizmos.put(gizmo.getName(), gizmo);
                this.gizmoSet.add(gizmo);
                Bukkit.getLogger().info("Gizmo Registered " + gizmo.getName());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, Gizmo> getGizmoMap() {
        return this.gizmos;
    }

    public Set<Gizmo> getGizmos() {
        return this.gizmoSet;
    }

}
