package me.itselliott.gizmos.gizmo;

import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott on 04/12/2015.
 */
public abstract class Gizmo implements Listener {

    private int cost;
    private String name;
    private ItemStack item;

    public Gizmo(int cost, String name, ItemStack item) {
        this.cost = cost;
        this.name = name;
        this.item = item;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return this.name;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public abstract void playGizmo(final Location location);
}
