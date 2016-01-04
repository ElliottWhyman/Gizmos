package me.itselliott.gizmos.inventory.menus;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.PaymentEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.ItemBuilder;
import me.itselliott.gizmos.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public class ConfirmationMenu extends Menu {

    private UUID paymentId;
    private ItemStack item;
    private int amount;

    public ConfirmationMenu(Menu parent, Menu child, UUID paymentId, ItemStack item, int amount) {
        super(parent, child, Constants.CONFIRMATION_MENU, 1);
        this.paymentId = paymentId;
        this.item = item;
        this.amount = amount;
        Gizmos.get().registerListener(this);
        this.populateMenu();
    }

    @Override
    public void populateMenu() {
        this.addItem(new ItemBuilder(Material.WOOL).setDamage((short) 5).setName(ChatColor.GREEN + "Accept").createItem(), 3);
        this.addItem(new ItemBuilder(Material.WOOL).setDamage((short) 14).setName(ChatColor.RED + "Cancel").createItem(), 5);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(this.getName())) {
            if (event.getCurrentItem().hasItemMeta()) {
                if (StringUtil.checkStrings(event.getCurrentItem().getItemMeta().getDisplayName(), "Accept")) {
                    Bukkit.getPluginManager().callEvent(new PaymentEvent(event.getActor(), this.paymentId, this.item, this.amount, true));
                } else if (StringUtil.checkStrings(event.getCurrentItem().getItemMeta().getDisplayName(), "Cancel")) {
                    event.getActor().closeInventory();
                    Bukkit.getPluginManager().callEvent(new PaymentEvent(event.getActor(), this.paymentId, this.item, this.amount, false));
                }
            }
            event.setCancelled(true);
        }
    }
}
