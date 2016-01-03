package me.itselliott.gizmos.inventory.menus;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.gizmo.gizmos.paintGun.Paint;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.PaymentMenu;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by Elliott2 on 03/01/2016.
 */
public class PaintMenu extends PaymentMenu {



    private Player player;
    private List<Paint> paints = Arrays.asList(
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)1).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)2).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)3).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)4).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)5).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)6).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)7).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)8).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)9).setName(Constants.PAINT_GIZMO).createItem() , 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)10).setName(Constants.PAINT_GIZMO).createItem(), 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)11).setName(Constants.PAINT_GIZMO).createItem(), 200),
            new Paint(new ItemBuilder(Material.STAINED_CLAY).setDamage((short)12).setName(Constants.PAINT_GIZMO).createItem(), 200),
            new Paint(new ItemBuilder(Material.GOLD_BLOCK).setName(Constants.PAINT_GIZMO).createItem()                       , 1000),
            new Paint(new ItemBuilder(Material.DIAMOND_BLOCK).setName(Constants.PAINT_GIZMO).createItem()                    , 1500)

    );

    public PaintMenu(Player player, Menu gizmoMenuParent) {
        super(Gizmos.get(), player, UUID.randomUUID(), Constants.PAINT_MENU, 7);
        this.setRows((int) Math.ceil((double) this.paints.size() / 9));
        this.player = player;
        this.setParent(gizmoMenuParent);
        this.populateMenu();
    }


    @Override
    public void populateMenu() {
        for (Paint paint : this.paints) {
            this.addItem(paint.getItemStack());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getName().equals(this.getName())) {
            for (Paint paint : this.paints) {
                if (paint.getItemStack().equals(event.getCurrentItem())) {
                    this.checkAndBuyGizmo(event.getActor(), paint.getCost(), paint.getItemStack());
                }
            }
            event.setCancelled(true);
        }
    }
}
