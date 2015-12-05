package me.itselliott.gizmos.gizmo.gizmos;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.RaindropReceiveEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.Hologram;
import me.itselliott.gizmos.utils.ItemBuilder;
import me.itselliott.gizmos.utils.Time;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Elliott on 04/12/2015.
 */
public class RaindropBomb extends Gizmo {

    private final int time = 5;
    private final int tntFuse = time*20; // 5 Seconds
    private final int radius = 10; // 10 Blocks



    private int taskID;

    public RaindropBomb() {
        super(Constants.RAINDROP_BOMB_COST, Constants.RAINDROP_BOMB, new ItemBuilder(Material.TNT).setName(ChatColor.RED + Constants.RAINDROP_BOMB).createItem());
        Gizmos.get().registerListener(this);
    }

    @Override
    public void playGizmo(final Location location) {
        TNTPrimed tntPrimed = location.getWorld().spawn(location, TNTPrimed.class);
        tntPrimed.setFuseTicks(this.tntFuse);
        tntPrimed.setYield(0);
        final Hologram countdown = new Hologram(location.setY(location.getY() - 0.5), ChatColor.BOLD + "" + ChatColor.GREEN + Time.formatTime(5, 0));
        this.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gizmos.get(), new Runnable() {
            int sec = 5;
            int ms = 0;
            int explodeTime = 0;
            @Override
            public void run() {
                if (sec <= 0) {
                    explodeTime++;
                    explode(location);
                    countdown.remove();
                    if (explodeTime >= 10) {
                        cancelTask();
                    }
                }
                if (ms <= 0) {
                    ms = 10;
                    sec--;
                }
                ms--;
                countdown.setText(Time.formatTime(sec, ms));
            }
        },0, 2);
    }

    private void cancelTask() {
        Bukkit.getScheduler().cancelTask(this.taskID);
    }

    private void explode(Location location) {

        for (Player player : location.getWorld().getPlayers()) {
            if (player.getLocation().distanceSquared(location) <= this.radius * this.radius) {
                // Plays a sound to the player
                player.playSound(location, Sound.FIZZ, 1, 0);

                // Spawn fireworks
                Firework firework = location.getWorld().spawn(location, Firework.class);
                FireworkMeta fireworkMeta = firework.getFireworkMeta();
                fireworkMeta.addEffects(FireworkEffect.builder().withColor(Color.AQUA, Color.WHITE, Color.RED).with(FireworkEffect.Type.BALL_LARGE).build());
                fireworkMeta.setPower(0);
                firework.setFireworkMeta(fireworkMeta);

                // Drops raindrops
                final Item item = location.getWorld().dropItem(location, new ItemStack(Material.GHAST_TEAR, 10));
                item.setPickupDelay(5*20);
                item.setVelocity(Vector.getRandom());
                Bukkit.getScheduler().scheduleSyncDelayedTask(Gizmos.get(), new Runnable() {
                    @Override
                    public void run() {
                        item.remove();
                    }
                }, 5*20);

                // Gives player raindrops
                Gizmos.get().getRaindropHandler().setRaindrops(player.getUniqueId(), Gizmos.get().getRaindropHandler().getRaindrops(player.getUniqueId()) + 10);

                // Calls raindrop event
                RaindropReceiveEvent raindropReceiveEvent = new RaindropReceiveEvent(player, 10);
                Bukkit.getPluginManager().callEvent(raindropReceiveEvent);
            }
        }
    }



}
