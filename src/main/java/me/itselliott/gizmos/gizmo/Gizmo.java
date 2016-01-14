package me.itselliott.gizmos.gizmo;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.menus.ConfirmationMenu;
import me.itselliott.gizmos.utils.ItemBuilder;
import me.itselliott.gizmos.utils.constants.GizmoConstants;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Created by Elliott on 04/12/2015.
 */
public abstract class Gizmo implements Listener {

    private int cost;
    private String name;
    private List<String> description;
    private ItemStack item;

    public Gizmo(GizmoConstants gizmoConstants, ItemStack item) {
        this.cost = gizmoConstants.cost();
        this.name = gizmoConstants.string();
        this.description = gizmoConstants.description();
        this.item = new ItemBuilder(item).setLore(this.description).createItem();
    }

    public Gizmo(String name, int cost, List<String> description, ItemStack item) {
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.item = new ItemBuilder(item).setLore(this.description).createItem();
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

    public List<String> getDescription() {
        return this.description;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    /**
     * Triggers the buy process for the Gimzo
     * @param player
     * @param gizmo Gizmo instance for the Gizmo
     */
    public void buy(Player player, Gizmo gizmo) {
        if (Gizmos.get().getRaindropHandler().canAfford(player.getUniqueId(), this.cost)) {
            new ConfirmationMenu(Gizmos.get(), gizmo, this.item).open(player);
        }
    }

    /**
     * What is performed when the Gizmo is clicked in an inventory
     * @param menu The Menu that will run this action is passed through
     */
    public abstract void clickAction(Menu menu, Player player);

    public abstract void registerListener();

}
