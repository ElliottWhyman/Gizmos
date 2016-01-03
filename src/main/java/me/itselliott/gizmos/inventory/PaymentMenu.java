package me.itselliott.gizmos.inventory;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.PaymentEvent;
import me.itselliott.gizmos.event.RaindropReceiveEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.menus.ConfirmationMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Elliott2 on 03/01/2016.
 */
public abstract class PaymentMenu extends Menu implements Listener {

    private Gizmos plugin;
    private Player player;
    private UUID paymentId;

    public PaymentMenu(Gizmos plugin, Player player, UUID paymentId, String name, int rows) {
        super(null, null, name, rows);
        this.plugin = plugin;
        this.player = player;
        this.paymentId = paymentId;
    }

    public void checkAndBuyGizmo(Player player, int cost, ItemStack itemStack) {
        if (this.plugin.getRaindropHandler().canAfford(player.getUniqueId(), cost)) {
            new ConfirmationMenu(this.getParent(), null, this.paymentId, itemStack, cost).open(player);
        } else player.sendMessage(ChatColor.RED + "Insufficient Funds");
    }

    @EventHandler
    public void onPayment(PaymentEvent event) {
        if (event.getPlayer().equals(this.player) && event.isAccepted() && event.getPaymentId().equals(this.paymentId)) {
            // Give player gizmo
            player.getInventory().setItem(4, event.getItem());
            player.closeInventory();
            // Subtract cost of gizmo
            this.plugin.getRaindropHandler().setRaindrops(player.getUniqueId(), this.plugin.getRaindropHandler().getRaindrops(player.getUniqueId()) - event.getAmount());
            Bukkit.getPluginManager().callEvent(new RaindropReceiveEvent(player, -event.getAmount()));
        }
    }
}
