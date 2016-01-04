package me.itselliott.gizmos.inventory.menus;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.PaymentEvent;
import me.itselliott.gizmos.event.RaindropReceiveEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.PaymentMenu;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoMenu extends PaymentMenu {

    private Set<Gizmo> gizmos;
    private Gizmos plugin;
    private Player player;
    private UUID paymentId;

    public GizmoMenu(Player player, UUID paymentId) {
        super(Gizmos.get(), player, paymentId, Constants.GIZMOS, 1);
        this.gizmos = new HashSet<>();
        this.gizmos.addAll(Gizmos.get().getRegistry().getGizmos());
        this.player = player;
        this.plugin = Gizmos.get();
        this.paymentId = paymentId;
        this.plugin.registerListener(this);
        this.setParent(this);
        this.populateMenu();
    }

    @Override
    public void populateMenu() {
        for (Gizmo gizmo : this.gizmos) {
            this.addItem(gizmo.getItem());
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().hasItemMeta() && event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
            if (StringUtil.checkStrings(event.getPlayer().getItemInHand().getItemMeta().getDisplayName(), Constants.GIZMOS)) {
                this.open(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && (event.getCurrentItem() != this.getForwardItem() || event.getCurrentItem() != this.getBackItem())) {
            Player player = (Player) event.getWhoClicked();
            if (StringUtil.checkStrings(event.getInventory().getName(), Constants.GIZMOS) && event.getCurrentItem().hasItemMeta()) {
                if (StringUtil.checkStrings(event.getCurrentItem().getItemMeta().getDisplayName(), Constants.PAINT_GIZMO)) { //TODO: prevent the need for special cases when using custom menus for a gizmo.
                    new PaintMenu(event.getActor(), this).open(event.getActor());
                } else {
                    Gizmo gizmo = this.plugin.getRegistry().getGizmoMap().get(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()));
                    this.checkAndBuyGizmo(player, gizmo.getCost(), gizmo.getItem());
                }
                event.setCancelled(true);
            }
        }
    }



}
