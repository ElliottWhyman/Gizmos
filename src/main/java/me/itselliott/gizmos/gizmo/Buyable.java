package me.itselliott.gizmos.gizmo;


import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 14/01/2016.
 */
public class Buyable {

    private ItemStack itemStack;
    private int cost;
    private String name;

    public Buyable(ItemStack itemStack, int cost, String name) {
        this.itemStack = new ItemBuilder(itemStack).setName(name).createItem();
        this.cost = cost;
        this.name = name;
    }

    public int getCost() {
        return this.cost;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public String getName() {
        return this.name;
    }
}
