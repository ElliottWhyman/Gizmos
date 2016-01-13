package me.itselliott.gizmos.inventory.interactables.clickables;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.inventory.interactables.Interactable;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 13/01/2016.
 */
public abstract class Clickable extends Interactable implements Click {

    public Clickable(ItemStack item) {
        super(item);
        Gizmos.get().registerListener(this);
    }
}
