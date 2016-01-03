package me.itselliott.gizmos.inventory;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.menus.GizmoMenu;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;


/**
 * Created by Elliott2 on 31/12/2015.
 */
public class MenuHandler implements Listener {

    public MenuHandler() {
        Gizmos.get().registerListener(this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem().getType().equals(Material.GHAST_TEAR) && event.getItem().hasItemMeta() && ChatColor.stripColor(event.getItem().getItemMeta().getDisplayName()).equals(Constants.GIZMOS)) {
            new GizmoMenu(event.getPlayer(), UUID.randomUUID()).open(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.getInventory().clear();
        // Gives player item to access the gizmo menu with
        player.getInventory().setItem(0, new ItemBuilder(Material.GHAST_TEAR).setName(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + Constants.GIZMOS).createItem());
        // Gives player gizmo place holder
        player.getInventory().setItem(4, new ItemBuilder(Material.THIN_GLASS).setName(ChatColor.BOLD + "" + ChatColor.AQUA + "Equipped Gizmo: None").createItem());
    }

}
