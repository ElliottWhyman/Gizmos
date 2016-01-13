package me.itselliott.gizmos.event.payment;

import me.itselliott.gizmos.gizmo.Gizmo;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Elliott2 on 03/01/2016.
 */
public class PaymentEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;
    private int cost;
    private ItemStack item;
    private boolean accepted;

    public PaymentEvent(Player player, int cost, ItemStack item, boolean accepted) {
        this.player = player;
        this.cost = cost;
        this.item = item;
        this.accepted = accepted;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getCost() {
        return this.cost;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public boolean isAccepted() {
        return this.accepted;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
