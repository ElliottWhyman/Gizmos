package me.itselliott.gizmos.event;

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
    private UUID id;
    private ItemStack item;
    private int amount;
    private boolean accepted;

    public PaymentEvent(Player player, UUID id, ItemStack item, int amount, boolean accepted) {
        this.player = player;
        this.id = id;
        this.item = item;
        this.amount = amount;
        this.accepted = accepted;
    }

    public Player getPlayer() {
        return this.player;
    }

    public UUID getPaymentId() {
        return this.id;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public int getAmount() {
        return this.amount;
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
