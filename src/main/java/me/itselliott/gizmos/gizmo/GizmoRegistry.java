package me.itselliott.gizmos.gizmo;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.gizmo.gizmos.RaindropBomb;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoRegistry {

    private final Gizmos plugin;
    private final Map<String, Gizmo> gizmos;

    private Class[] gizmoClasses = {
            RaindropBomb.class
    };

    public GizmoRegistry(Gizmos plugin) {
        this.plugin = plugin;
        this.gizmos = new HashMap<>();
        for (Class gizmo : this.gizmoClasses) {
            try {
                Gizmo giz = (Gizmo) gizmo.getConstructor().newInstance();
                this.gizmos.put(giz.getName(), giz);
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                Bukkit.getLogger().severe("Unable to load " + gizmo.getName());
                e.printStackTrace();
            }
        }
        for (Gizmo gizmo : this.gizmos.values()) {
            this.plugin.getServer().getPluginManager().registerEvents(gizmo, this.plugin);
            Bukkit.getLogger().info("Loaded gizmo " + gizmo.getName());
        }
    }

    public Map<String, Gizmo> getGizmos() {
        return this.gizmos;
    }

    public Set<Gizmo> getGizmosSet() {
        Set<Gizmo> gizmos = new HashSet<>();
        for (Gizmo gizmo : this.gizmos.values()) {
            gizmos.add(gizmo);
        }
        return gizmos;
    }

}
