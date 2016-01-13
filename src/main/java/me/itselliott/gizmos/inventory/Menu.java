package me.itselliott.gizmos.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public abstract class Menu {

    private String name;
    private int rows;

    private Inventory inventory;

    public Menu(String name, int rows) {
        this.name = name;
        this.rows = rows;

        this.inventory = Bukkit.createInventory(null, this.rows * 9, this.name);
    }

    public abstract void populateMenu();

    /**
     * Adds a array of items into the next available slot
     * @param itemStacks array of items toa dd
     */
    public void addItem(ItemStack... itemStacks) {
        for (ItemStack itemStack : itemStacks)
            this.inventory.addItem(itemStack);
    }

    /**
     * Adds an item to the inventory menu
     * @param itemStack Itemstack to add
     * @param slot Slot where the item goes in
     */
    public void addItem(ItemStack itemStack, int slot) {
        this.inventory.setItem(slot, itemStack);
    }

    /**
     * Adds an item to the inventory menu
     * @param material Item material type
     * @param amount Amount of the item
     * @param slot Slot where the item goes in
     */
    public void addItem(Material material, int amount, int slot) {
        this.inventory.setItem(slot, new ItemStack(material, amount));
    }

    /**
     * Removes an item for the inventory
     * @param slot Slot where the item will be cleared
     */
    public void removeItem(int slot) {
        this.inventory.clear(slot);
    }

    /**
     * Empties out entire inventory
     */
    public void clear() {
        this.inventory.clear();
    }

    /**
     * Resets the number of rows to the given variable
     * @param rows New amount of rows
     */
    public void setRows(int rows) {
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, rows * 9, this.name);
    }

    /**
     * Open this inventory menu
     * @param player Player to open the inventory
     */
    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    /**
     * Close this inventory menu
     * @param player Player to close the inventory
     */
    public void close(Player player) {
        player.closeInventory();
    }

    /**
     * @return Returns name of inventory
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return Returns the number of rows in the menu, each row has 9 slots
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * @return this inventory menu
     */
    public Inventory getInventory() {
        return this.inventory;
    }

}
