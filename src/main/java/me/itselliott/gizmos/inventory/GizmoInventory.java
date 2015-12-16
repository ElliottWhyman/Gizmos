package me.itselliott.gizmos.inventory;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.RaindropReceiveEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.utils.Constants;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoInventory implements Listener {

    private Inventory inventory;
    private Set<Gizmo> gizmos;
    private Gizmos plugin;

    public GizmoInventory(Gizmos plugin) {
        this.inventory = Bukkit.createInventory(null, 9, ChatColor.DARK_PURPLE + Constants.GIZMOS);
        this.gizmos = Gizmos.get().getRegistry().getGizmosSet();
        this.addGizmos();
        this.plugin = plugin;
        this.plugin.registerListener(this);
    }

    private void addGizmos() {
        for (Gizmo gizmo : this.gizmos) {
            this.inventory.addItem(gizmo.getItem());
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().hasItemMeta() && event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
            if (ChatColor.stripColor(event.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals(Constants.GIZMOS)) {
                event.getPlayer().openInventory(this.inventory);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (ChatColor.stripColor(event.getInventory().getName()).equals(Constants.GIZMOS)) {
            Validate.notNull(this.plugin.getRegistry().getGizmos().get(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
            Gizmo gizmo = this.plugin.getRegistry().getGizmos().get(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
            if (this.plugin.getRaindropHandler().canAfford(player.getUniqueId(), gizmo.getCost())) {
                // Give player gizmo
                player.getInventory().addItem(gizmo.getItem());
                player.closeInventory();
                // Subtract cost of gizmo
                this.plugin.getRaindropHandler().setRaindrops(player.getUniqueId(), this.plugin.getRaindropHandler().getRaindrops(player.getUniqueId()) - gizmo.getCost());
                Bukkit.getPluginManager().callEvent(new RaindropReceiveEvent(player, -gizmo.getCost()));
                event.setCancelled(true);
            }
        }
    }

}
