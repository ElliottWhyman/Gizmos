package me.itselliott.gizmos;

import me.itselliott.gizmos.event.RaindropReceiveEvent;
import me.itselliott.gizmos.event.RaindropUpdateEvent;
import me.itselliott.gizmos.gizmo.GizmoListener;
import me.itselliott.gizmos.gizmo.GizmoRegistry;
import me.itselliott.gizmos.gizmo.gizmos.RaindropBomb;
import me.itselliott.gizmos.inventory.GizmoInventory;
import me.itselliott.gizmos.inventory.InventoryListener;
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

    private final int raindropValue = 1000; // Default amount of raindrops given to players

    private GizmoRegistry registry;
    private GizmoInventory gizmoInventory;
    private RaindropHandler raindropHandler;
    private InventoryListener inventoryListener;
    private GizmoListener gizmoListener;

    private HashMap<UUID, RaindropScoreboard> raindropScoreboards;

    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(this, this);
        this.registry = new GizmoRegistry(this);
        this.gizmoInventory = new GizmoInventory();
        this.raindropHandler = new RaindropHandler();
        this.inventoryListener = new InventoryListener(this);
        this.gizmoListener = new GizmoListener(this);
        this.raindropScoreboards = new HashMap<>();
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
        // Give the player a scoreboard with raindrop infromation
        RaindropScoreboard scoreboard = new RaindropScoreboard(event.getPlayer(), this.raindropHandler.getRaindrops(event.getPlayer().getUniqueId()));
        event.getPlayer().setScoreboard(scoreboard.getScoreboard());
        this.raindropScoreboards.put(event.getPlayer().getUniqueId(), scoreboard);
    }

    @EventHandler
    public void onRaindropRecieve(RaindropReceiveEvent event) {
        event.getPlayer().sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "+" + event.getAmount() + ChatColor.RESET + "" + ChatColor.DARK_PURPLE + " | " + ChatColor.WHITE + "Raindrops Received");
        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1, 0);

        Bukkit.getPluginManager().callEvent(new RaindropUpdateEvent(event.getPlayer()));
    }

    @EventHandler
    public void onRaindropUpdate(RaindropUpdateEvent event) {
        // Updates the scoreboard to show new raindrop count
        this.raindropScoreboards.get(event.getPlayer().getUniqueId()).update();
    }

    @EventHandler
    public void onchat(AsyncPlayerChatEvent event) {
        if (event.getMessage().equalsIgnoreCase("raindrop bomb")) {
            RaindropBomb raindropBomb = new RaindropBomb();
            raindropBomb.playGizmo(event.getPlayer().getLocation());
        }
    }

}
