package me.itselliott.gizmos.inventory;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

import java.util.Set;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoInventory {

    private Inventory inventory;
    private Set<Gizmo> gizmos;

    public GizmoInventory() {
        this.inventory = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + Constants.GIZMOS);
        this.gizmos = Gizmos.get().getRegistry().getGizmosSet();

        this.addGizmos();
    }

    private void addGizmos() {
        for (Gizmo gizmo : this.gizmos) {
            this.inventory.addItem(gizmo.getItem());
        }
    }

    public Inventory getInvetory() {
        return this.inventory;
    }

}
