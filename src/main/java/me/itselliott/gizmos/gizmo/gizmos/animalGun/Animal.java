package me.itselliott.gizmos.gizmo.gizmos.animalGun;

import me.itselliott.gizmos.gizmo.Buyable;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 14/01/2016.
 */
public class Animal extends Buyable {

    private EntityType animal;

    public Animal(EntityType entityType, int cost, ItemStack itemStack, String name) {
        super(itemStack, cost, name);
        this.animal = entityType;
    }

    public EntityType getAnimal() {
        return this.animal;
    }

}
