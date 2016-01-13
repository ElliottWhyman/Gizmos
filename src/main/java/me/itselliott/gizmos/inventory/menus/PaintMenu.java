package me.itselliott.gizmos.inventory.menus;

import me.itselliott.gizmos.gizmo.gizmos.paintGun.Paint;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.interactables.clickables.Clickable;
import me.itselliott.gizmos.utils.Constants;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Elliott2 on 03/01/2016.
 */
public class PaintMenu extends Menu {

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

    private Menu parent;

    public PaintMenu(Menu parent) {
        super(Constants.PAINT_MENU, 7);
        this.parent = parent;
        this.setRows((int) Math.ceil((double) (this.paints.size() + 1) / 9));
        this.populateMenu();
    }


    @Override
    public void populateMenu() {
        for (final Paint paint : this.paints) {
            this.addItem(new Clickable(paint.getItemStack()) {
                @Override
                @EventHandler
                public void action(InventoryClickEvent event) {
                    if (event.getCurrentItem().equals(paint.getItemStack())) {
                        GizmoUtil.checkAndBuyGizmo(event.getActor(), paint.getCost(), paint.getItemStack());
                        event.setCancelled(true);
                    }
                }
            }.getItemStack());
        }
        this.addItem(new Clickable(new ItemBuilder(Material.WOOD).setName("Back").createItem()) {
            @Override
            @EventHandler
            public void action(InventoryClickEvent event) {
                if (event.getCurrentItem().equals(this.getItemStack())) {
                    event.getActor().closeInventory();
                    parent.open(event.getActor());
                    event.setCancelled(true);
                }
            }
        }.getItemStack());
    }
}
