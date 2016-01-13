package me.itselliott.gizmos.inventory.interactables.useables;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.inventory.interactables.Interactable;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 13/01/2016.
 */
public abstract class Usable extends Interactable implements Use {

    public Usable(ItemStack item) {
        super(item);
        Gizmos.get().registerListener(this);
    }

}
