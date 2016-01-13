package me.itselliott.gizmos.inventory.interactables;

import me.itselliott.gizmos.Gizmos;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 13/01/2016.
 */
public class Interactable implements Listener {

    private ItemStack itemStack;

    public Interactable(org.bukkit.inventory.ItemStack item) {
        this.itemStack = item;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

}
