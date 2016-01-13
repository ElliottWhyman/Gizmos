package me.itselliott.gizmos.gizmo.gizmos.raindropBomb;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.event.raindrop.RaindropUpdateEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;


/**
 * Created by Elliott on 04/12/2015.
 */
public class RaindropBombGizmo extends Gizmo {

    private final int time = 3;
    private final int timeInTicks = time * 20; // 3 Seconds
    private final int radius = 10; // 10 Blocks
    private Player player;

    boolean cancel = false;

    private int[] taskIDs = new int[2];

    public RaindropBombGizmo() {
        super(Constants.RAINDROP_BOMB, Constants.RAINDROP_BOMB_COST, new ItemBuilder(Material.TNT).setName(ChatColor.RED + Constants.RAINDROP_BOMB).createItem());
    }

    @EventHandler
    public void onGizmoUse(GizmoUseEvent event) {
        if (!event.isCancelled() && event.getGizmo() == this) {
            this.player = event.getPlayer();
            this.spawnTNT(this.player);
        }
    }

    public void spawnTNT(final Player player) {
        final Location location = player.getLocation();
        TNTPrimed tntPrimed = location.getWorld().spawn(location, TNTPrimed.class);
        tntPrimed.setFuseTicks(this.timeInTicks);
        tntPrimed.setYield(0);
        final Hologram countdown = new Hologram(location.setY(location.getY() - 0.5), ChatColor.BOLD + "" + ChatColor.RED + Time.formatTime(5, 0));
        this.taskIDs[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gizmos.get(), new Runnable() {
            int sec = time;
            int ms = 0;
            int explodeTime = 0;

            @Override
            public void run() {
                if (sec <= 0) {
                    explodeTime++;
                    explode(location);
                    countdown.remove();
                    if (explodeTime >= 10) {
                        cancelTask(0,1);
                        cancel = true;
                    }
                }
                if (ms <= 0) {
                    ms = 10;
                    sec--;
                }
                ms--;
                countdown.setText(ChatColor.RED + Time.formatTime(sec, ms));
            }
        }, 0, 2);
    }

    private void cancelTask(int... indexs) {
        for (int index : indexs) {
            Bukkit.getScheduler().cancelTask(this.taskIDs[index]);
        }
    }

    private void explode(final Location location) {
        for (Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().distanceSquared(location) <= this.radius * this.radius) {
                // Plays a sound to nearby players
                player.playSound(location, Sound.FIZZ, 1, 0);
            }
        }

        // Spawn fireworks
        Firework firework = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffects(FireworkEffect.builder().withColor(Color.AQUA, Color.WHITE, Color.RED).with(FireworkEffect.Type.BALL_LARGE).build());
        fireworkMeta.setPower(0);
        firework.setFireworkMeta(fireworkMeta);

        // Drops raindrops
        this.taskIDs[1] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gizmos.get(), new Runnable() {
            @Override
            public void run() {
                if (!cancel) {
                    Item item = location.getWorld().dropItem(location, new ItemStack(Material.GHAST_TEAR, 1));
                    item.setVelocity(Vector.getRandom());
                }
            }
        }, 0, 2);
        GizmoUtil.remove(player);
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (event.getItem().getItemStack().getType().equals(Material.GHAST_TEAR)) {
            // Gives player raindrops on pickup
            Gizmos.get().getRaindropHandler().setRaindrops(event.getPlayer().getUniqueId(), Gizmos.get().getRaindropHandler().getRaindrops(event.getPlayer().getUniqueId()) + 1);
            // Call raindrop event
            RaindropUpdateEvent raindropReceiveEvent = new RaindropUpdateEvent(event.getPlayer(), 10);
            Bukkit.getPluginManager().callEvent(raindropReceiveEvent);

            // Remove item from world
            event.setCancelled(true);
            event.getItem().remove();
        }
    }

    @Override
    public void clickAction(Menu menu, Player player) {
        GizmoUtil.checkAndBuyGizmo(player, this, this.getItem());
    }

    @Override
    public void registerListener() {
        Gizmos.get().registerListener(this);
    }
}
