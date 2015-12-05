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

/**
 * Created by Elliott on 05/12/2015.
 */
public class InventoryListener implements Listener {

    private Gizmos plugin;

    public InventoryListener(Gizmos plugin) {
        this.plugin = plugin;
        plugin.registerListener(this);
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().hasItemMeta() && event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
            if (ChatColor.stripColor(event.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equals(Constants.GIZMOS)) {
                event.getPlayer().openInventory(this.plugin.getGizmoInventory().getInvetory());
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
