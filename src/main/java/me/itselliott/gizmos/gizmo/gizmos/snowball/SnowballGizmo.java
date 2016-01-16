package me.itselliott.gizmos.gizmo.gizmos.snowball;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import org.apache.commons.lang.Validate;
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

import static me.itselliott.gizmos.utils.constants.GizmoConstants.SNOWBALL;

/**
 * Created by Elliott on 06/12/2015.
 */
public class SnowballGizmo extends Gizmo {

    private Player player;

    public SnowballGizmo() {
        super(SNOWBALL, new ItemBuilder(Material.SNOW_BALL).setName(ChatColor.BOLD + SNOWBALL.name()).createItem());
        Gizmos.get().registerListener(this);
    }

    @EventHandler
    public void onGizmoUse(GizmoUseEvent event) {
        this.player = event.getPlayer();
    }

    // Current issue with sending the equipment packet for a helmet - works with item in hand.
    private void sendEquipmentPacket(Player player, Player reciever) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityEquipment(reciever.getEntityId(),0, CraftItemStack.asNMSCopy(new ItemStack(Material.CHAINMAIL_HELMET))));
        GizmoUtil.remove(player);
    }

    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent event) {
        Validate.notNull(this.player);
        if (event.getDamager() instanceof Snowball && event.getEntity() instanceof Player) {
            if (event.getEntity() == this.player) {
                Snowball snowball = (Snowball) event.getDamager();
                Bukkit.broadcastMessage(snowball.getShooter().toString() + " " + event.getEntity().toString());
                this.sendEquipmentPacket((Player) snowball.getShooter(), (Player) event.getEntity());
            }
        }
    }

    @Override
    public void clickAction(Menu menu, Player player) {
        GizmoUtil.checkAndBuyGizmo(player, this, this.getItem());
    }

}
