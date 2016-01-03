package me.itselliott.gizmos.gizmo.gizmos.paintGun;

import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 03/01/2016.
 */
public class Paint {

    private ItemStack itemStack;
    private int cost;

    public Paint(ItemStack itemStack, int cost) {
        this.itemStack = itemStack;
        this.cost = cost;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public int getCost() {
        return this.cost;
    }
}
