package me.itselliott.gizmos.gizmo.gizmos;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Elliott on 06/12/2015.
 */
public class SnowballGizmo extends Gizmo {

    public SnowballGizmo() {
        super(Constants.SNOWBALL_GIZMO, Constants.SNOWBALL_GIZMO_COST,
                new ItemBuilder(Material.SNOW_BALL).setName(ChatColor.BOLD + Constants.SNOWBALL_GIZMO).createItem());
    }

    // Current issue with sending the equipment packet for a helmet - works with item in hand.
    private void sendEquipmentPacket(Player player, Player reciever) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(reciever.getEntityId(),0, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_HELMET))));
    }

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) {
            Snowball snowball = (Snowball) event.getDamager();
            Bukkit.broadcastMessage(snowball.getShooter().toString() + " " + event.getEntity().toString());
            this.sendEquipmentPacket((Player)snowball.getShooter(), (Player) event.getEntity());
        }
    }

    @Override
    public void registerListener() {
        Gizmos.get().registerListener(this);
    }
}
