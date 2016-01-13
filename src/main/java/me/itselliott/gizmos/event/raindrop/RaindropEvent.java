package me.itselliott.gizmos.event.raindrop;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Elliott on 05/12/2015.
 */
public class RaindropEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private Player player;

    public RaindropEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }


    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}

