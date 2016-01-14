package me.itselliott.gizmos.inventory.menus;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.event.payment.PaymentEvent;
import me.itselliott.gizmos.event.raindrop.RaindropUpdateEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.interactables.clickables.Clickable;
import me.itselliott.gizmos.inventory.interactables.useables.Usable;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import me.itselliott.gizmos.utils.constants.StringConstants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public class ConfirmationMenu extends Menu {

    private Gizmos plugin;
    private Gizmo gizmo;
    private ItemStack item;

    public ConfirmationMenu(Gizmos plugin, Gizmo gizmo, ItemStack item) {
        super(StringConstants.CONFIRMATION.name(), 1);
        this.plugin = plugin;
        this.gizmo = gizmo;
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
                    buy(gizmo, event.getActor());
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
                    Bukkit.getPluginManager().callEvent(new PaymentEvent(event.getActor(), gizmo, item, false));
                    event.setCancelled(true);
                }
            }
        }.getItemStack(), 5);
    }

    private void buy(final Gizmo gizmo, final Player player) {
        // Give player gizmo and handling the Gizmo loading
        player.getInventory().setItem(4, new Usable(this.item) {
            @Override
            @EventHandler
            public void action(PlayerInteractEvent event) {
                if (event.getItem().equals(this.getItemStack()) && event.getActor().equals(player) && !GizmoUtil.hasGizmos(event.getPlayer())) {
                    gizmo.registerListener();
                    // Put player using the gizmo in global map
                    GizmoUtil.playerGizmos.put(event.getActor().getName(), gizmo);
                    // Calls the event that triggers the gizmo
                    Bukkit.getPluginManager().callEvent(new GizmoUseEvent(player, gizmo, player.getLocation()));
                    event.setCancelled(true);
                }
            }
        }.getItemStack());
        player.closeInventory();
        // Subtract cost of gizmo
        this.plugin.getRaindropHandler().setRaindrops(player.getUniqueId(), this.plugin.getRaindropHandler().getRaindrops(player.getUniqueId()) - this.gizmo.getCost());
        // Call events
        Bukkit.getPluginManager().callEvent(new RaindropUpdateEvent(player, -this.gizmo.getCost()));
        Bukkit.getPluginManager().callEvent(new PaymentEvent(player, this.gizmo, this.item, true));
    }

}
