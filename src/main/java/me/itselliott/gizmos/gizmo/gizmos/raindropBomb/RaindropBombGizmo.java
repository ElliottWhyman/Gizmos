package me.itselliott.gizmos.gizmo.gizmos.raindropBomb;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.utils.*;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import static me.itselliott.gizmos.utils.constants.GizmoConstants.RAINDROP_BOMB;

/**
 * TODO: Re-implement hologram countdown
 *
 * Created by Elliott on 04/12/2015.
 */
public class RaindropBombGizmo extends Gizmo {

    private final int time = 3;
    private final int timeInTicks = time * 20; // 3 Seconds
    private final int radius = 10; // 10 Blocks
    private Player player;

    private int taskIds[] = new int[2];

    public RaindropBombGizmo() {
        super(RAINDROP_BOMB, new ItemBuilder(Material.TNT).setName(ChatColor.RED + RAINDROP_BOMB.string()).createItem());
    }

    @EventHandler
    public void onGizmoUse(GizmoUseEvent event) {
        if (!event.isCancelled() && event.getGizmo() == this) {
            this.player = event.getPlayer();
            this.spawnTNT(this.player);
            this.disableReaptingTasks();
        }
    }

    public void spawnTNT(final Player player) {
        final Location location = player.getLocation();
        TNTPrimed tntPrimed = location.getWorld().spawn(location, TNTPrimed.class);
        tntPrimed.setFuseTicks(this.timeInTicks);
        tntPrimed.setYield(0);
        tntPrimed.setMetadata("raindrop-bomb", new FixedMetadataValue(Gizmos.get(), true));
    }

    @EventHandler
    public void onTntExplode(EntityExplodeEvent event) {
        if (event.getActor() instanceof TNTPrimed && event.getActor().hasMetadata("raindrop-bomb")) {
            this.explode(event.getLocation());
        }
    }

    private void explode(final Location location) {
        for (Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().distanceSquared(location) <= Math.pow(this.radius, 2)) {
                // Plays a sound to nearby players
                player.playSound(location, Sound.FIZZ, 1, 0);
            }
        }
        this.spawnFireworks(location);
        this.dropRaindrops(location);
        GizmoUtil.remove(player);
    }

    private void spawnFireworks(final Location location) {
        this.taskIds[0] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gizmos.get(), new BukkitRunnable() {
            @Override
            public void run() {
                Firework firework = location.getWorld().spawn(location, Firework.class);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();
                fireworkMeta.addEffects(FireworkEffect.builder().withColor(Color.AQUA, Color.WHITE, Color.RED).with(FireworkEffect.Type.BALL_LARGE).build());
                fireworkMeta.setPower(0);
                firework.setFireworkMeta(fireworkMeta);
            }
        }, 0, 2);
    }

    private void dropRaindrops(final Location location) {
        this.taskIds[1] = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gizmos.get(), new Runnable() {
            @Override
            public void run() {
                Item item = location.getWorld().dropItem(location, new ItemStack(Material.GHAST_TEAR, 1));
                item.setVelocity(Vector.getRandom());
            }
        }, 0, 2);
    }

    private void disableReaptingTasks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int i : taskIds) {
                    Bukkit.getScheduler().cancelTask(i);
                }
            }
        }.runTaskLater(Gizmos.get(), 20 + this.timeInTicks);

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
