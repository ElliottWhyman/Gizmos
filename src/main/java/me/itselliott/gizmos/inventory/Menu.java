package me.itselliott.gizmos.inventory;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public abstract class Menu implements Listener {

    private Menu parent;
    private Menu child;
    private String name;
    private int rows;

    private Inventory inventory;

    private ItemStack back;
    private ItemStack forward;

    public Menu(Menu parent, Menu child, String name, int rows) {
        this.parent = parent;
        this.child = child;
        this.name = name;
        this.rows = rows;

        this.back = new ItemBuilder(Material.EYE_OF_ENDER).setName(ChatColor.RED + "Back").createItem();
        this.forward = new ItemBuilder(Material.EYE_OF_ENDER).setName(ChatColor.GREEN + "Forward").createItem();

        this.inventory = Bukkit.createInventory(null, this.rows * 9, this.name);
        this.inventory.setItem(0, this.back);
        this.inventory.setItem(8, this.forward);

        Gizmos.get().registerListener(this);

    }

    public abstract void populateMenu();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(this.name) && event.getCurrentItem().getType().equals(Material.EYE_OF_ENDER)) {
            if (event.getCurrentItem().hasItemMeta()) {
                Bukkit.broadcastMessage("yes");
                if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Back")) {
                    this.back(event.getActor());
                } else if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals("Forward")) {
                    this.next(event.getActor());
                }
            }
        }
        event.setCancelled(true);
    }

    /**
     * @param player Player to open next inventory
     */
    public void next(Player player) {
        close(player);
        if (this.child != null)
            this.child.open(player);
    }

    /**
     * @param player Player to open previous inventory
     */
    public void back(Player player) {
        close(player);
        if (this.parent != null)
            this.parent.open(player);
    }

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
     * Set a parent inventory which a player can use to go back
     * @param parent Parent inventory
     */
    public void setParent(Menu parent) {
        this.parent = parent;
    }

    /**
     * Set a child inventory which a player can use to go forward
     * @param child Child inventory
     */
    public void setChild(Menu child) {
        this.child = child;
    }

    /**
     * Gets the back menu
     * @return Parent menu
     */
    public Menu getParent() {
        return this.parent;
    }

    /**
     * Gets the forward menu
     * @return Child menu
     */
    public Menu getChild() {
        return this.child;
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

    /**
     * @return Returns the itemstack used to take a player forward in menus
     */
    public ItemStack getForwardItem() {
        return this.forward;
    }

    /**
     * @return Returns the itemstack used to take a player forward in menus
     */
    public ItemStack getBackItem() {
        return this.back;
    }
}
