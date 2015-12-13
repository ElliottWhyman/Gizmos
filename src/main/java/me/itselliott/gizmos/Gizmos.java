package me.itselliott.gizmos;

import me.itselliott.gizmos.event.RaindropReceiveEvent;
import me.itselliott.gizmos.event.RaindropUpdateEvent;
import me.itselliott.gizmos.gizmo.GizmoListener;
import me.itselliott.gizmos.gizmo.GizmoRegistry;
import me.itselliott.gizmos.gizmo.gizmos.RaindropBombGizmo;
import me.itselliott.gizmos.inventory.GizmoInventory;
import me.itselliott.gizmos.raindrops.RaindropHandler;
import me.itselliott.gizmos.raindrops.RaindropScoreboard;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

/**
 * Mock up plugin to mimic Overcast Networks Gizmos'
 *
 * Created by Elliott on 04/12/2015.
 */
public class Gizmos extends JavaPlugin implements Listener {

    private static Gizmos instance;

    private final int raindropValue = 10000; // Default amount of raindrops given to players

    private GizmoRegistry registry;
    private GizmoInventory gizmoInventory;
    private RaindropHandler raindropHandler;
    private GizmoListener gizmoListener;

    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(this, this);
        this.registry = new GizmoRegistry(this);
        this.gizmoInventory = new GizmoInventory(this);
        this.raindropHandler = new RaindropHandler();
        this.gizmoListener = new GizmoListener(this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    /**
     * @return an instance of the main Plugin class
     */
    public static Gizmos get() {
        return instance;
    }


    public RaindropHandler getRaindropHandler() {
        return this.raindropHandler;
    }

    public GizmoRegistry getRegistry() {
        return this.registry;
    }

    public GizmoInventory getGizmoInventory() {
        return this.gizmoInventory;
    }

    public void registerListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        // Gives players a set a amount of raindrops on connect for mock up purposes
        this.raindropHandler.setRaindrops(event.getPlayer().getUniqueId(), this.raindropValue);
        // Gives player item to access the gizmo menu with
        event.getPlayer().getInventory().addItem(new ItemBuilder(Material.GHAST_TEAR).setName(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + Constants.GIZMOS).createItem());
        // Give the player a scoreboard with raindrop information
        RaindropScoreboard scoreboard = new RaindropScoreboard(event.getPlayer(), this.raindropHandler.getRaindrops(event.getPlayer().getUniqueId()));
        event.getPlayer().setScoreboard(scoreboard.getScoreboard());
        this.raindropHandler.getRaindropScoreboards().put(event.getPlayer().getUniqueId(), scoreboard);
    }



}
