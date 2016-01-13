package me.itselliott.gizmos.inventory.menus;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.payment.PaymentEvent;
import me.itselliott.gizmos.event.raindrop.RaindropReceiveEvent;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.interactables.clickables.Clickable;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public class ConfirmationMenu extends Menu {

    private Gizmos plugin;
    private int cost;
    private ItemStack item;

    public ConfirmationMenu(Gizmos plugin, int cost, ItemStack item) {
        super(Constants.CONFIRMATION_MENU, 1);
        this.plugin = plugin;
        this.cost = cost;
        this.item = item;
        this.populateMenu();
    }

    @Override
    public void populateMenu() {
        this.addItem(new Clickable(new ItemBuilder(Material.WOOL).setDamage((short) 5).setName(ChatColor.GREEN + "Accept").createItem()) {
            @Override
            @EventHandler
            public void action(InventoryClickEvent event) {
                if (event.getCurrentItem().equals(this.getItemStack())) {
                    buy(cost, event.getActor());
                    event.setCancelled(true);
                }
            }
        }.getItemStack(), 3);

        this.addItem(new Clickable(new ItemBuilder(Material.WOOL).setDamage((short) 14).setName(ChatColor.RED + "Cancel").createItem()) {
            @Override
            @EventHandler
            public void action(InventoryClickEvent event) {
                if (event.getCurrentItem().equals(this.getItemStack())) {
                    event.getActor().closeInventory();
                    Bukkit.getPluginManager().callEvent(new PaymentEvent(event.getActor(), cost, item, false));
                    event.setCancelled(true);
                }
            }
        }.getItemStack(), 5);
    }

    private void buy(int cost, Player player) {
        // Give player gizmo
        player.getInventory().setItem(4, this.item);
        player.closeInventory();
        // Subtract cost of gizmo
        this.plugin.getRaindropHandler().setRaindrops(player.getUniqueId(), this.plugin.getRaindropHandler().getRaindrops(player.getUniqueId()) - cost);
        // Call events
        Bukkit.getPluginManager().callEvent(new RaindropReceiveEvent(player, -cost));
        Bukkit.getPluginManager().callEvent(new PaymentEvent(player, this.cost, this.item, true));
    }

}
