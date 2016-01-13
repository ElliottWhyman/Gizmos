package me.itselliott.gizmos.gizmo.gizmos.paintGun;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.menus.PaintMenu;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Elliott2 on 22/12/2015.
 */
public class PaintGizmo extends Gizmo {

    private boolean toggle;
    private Player player;
    private Paint paint;
    private HashMap<Location, Material> blocks;

    public PaintGizmo() {
        super(Constants.PAINT_GIZMO, Constants.PAINT_GIZMO_COST, new ItemBuilder(Material.STAINED_CLAY).setName(ChatColor.RED + Constants.PAINT_GIZMO).createItem());
        this.paint = new Paint(new ItemBuilder(Material.STAINED_CLAY).createItem(), 0);
    }

    public PaintGizmo(Paint paint) {
        super(Constants.PAINT_GIZMO, Constants.PAINT_GIZMO_COST + paint.getCost(), new ItemBuilder(Material.STAINED_CLAY).setName(ChatColor.RED + Constants.PAINT_GIZMO).createItem());
        this.paint = paint;
    }

    @EventHandler
    public void onGizmoUse(GizmoUseEvent event) {
        if (!event.isCancelled() && event.getGizmo() == this) {
            this.toggle = true;
            this.player = event.getPlayer();
            this.blocks = new HashMap<>();
            this.setStopTime(player);
        }
    }

    private void setStopTime(final Player player) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Gizmos.get(), new Runnable() {
            @Override
            public void run() {
                toggle = false;
                GizmoUtil.remove(player);
            }
        }, 10 * 20); // 10 Seconds
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer() == this.player && this.toggle) {
            Block block = event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN);
            if (block.getType() != Material.AIR) {
                if (!this.blocks.containsKey(block.getLocation())) {
                    Material material = block.getType();
                    byte data = block.getData();
                    this.blocks.put(block.getLocation(), material);
                    this.setBlock(block, material, data);
                }
            }
        }
    }

    private void setBlock(final Block block, final Material oldType, final byte data) {
        block.setType(this.paint.getItemStack().getType());
        block.setData((byte) this.paint.getItemStack().getDurability());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Gizmos.get(), new Runnable() {
            @Override
            public void run() {
                block.setType(oldType);
                block.setData(data);
            }
        }, 2 * 20);
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public void clickAction(Menu menu, Player player) {
        new PaintMenu(menu).open(player);
    }

    @Override
    public void registerListener() {
        Gizmos.get().registerListener(this);
    }
}
