package me.itselliott.gizmos;

import me.itselliott.gizmos.gizmo.GizmoRegistry;
import me.itselliott.gizmos.inventory.MenuHandler;
import me.itselliott.gizmos.inventory.menus.GizmoMenu;
import me.itselliott.gizmos.raindrops.RaindropHandler;
import me.itselliott.gizmos.raindrops.RaindropScoreboard;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Mock up plugin to mimic Overcast Networks Gizmos'
 *
 * Created by Elliott on 04/12/2015.
 */
public class Gizmos extends JavaPlugin implements Listener {

    private static Gizmos instance;

    private final int raindropValue = 10000; // Default amount of raindrops given to players

    private GizmoRegistry registry;
    private RaindropHandler raindropHandler;
    private MenuHandler menuHandler;

    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(this, this);
        this.registry = new GizmoRegistry();
        this.registry.registerLoaders(); // Registers all GizmoLoaders which trigger the gizmos when used
        this.raindropHandler = new RaindropHandler(this);
        this.menuHandler = new MenuHandler();
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

    public void registerListener(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        // Gives players a set a amount of raindrops on connect for mock up purposes
        this.raindropHandler.setRaindrops(event.getPlayer().getUniqueId(), this.raindropValue);
        // Give the player a scoreboard with raindrop information
        RaindropScoreboard scoreboard = new RaindropScoreboard(player, this.raindropHandler.getRaindrops(player.getUniqueId()));
        player.setScoreboard(scoreboard.getScoreboard());
        this.raindropHandler.getRaindropScoreboards().put(player.getUniqueId(), scoreboard);
    }
}
