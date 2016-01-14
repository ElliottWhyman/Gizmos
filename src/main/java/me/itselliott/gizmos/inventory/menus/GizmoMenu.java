package me.itselliott.gizmos.inventory.menus;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.interactables.clickables.Clickable;
import me.itselliott.gizmos.utils.constants.StringConstants;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Elliott on 05/12/2015.
 */
public class GizmoMenu extends Menu {

    private List<Gizmo> gizmos;
    private Menu menu;

    public GizmoMenu() {
        super(StringConstants.GIZMOS.name(), 1);
        this.gizmos = new ArrayList<>();
        this.gizmos.addAll(Gizmos.get().getRegistry().getGizmos());
        this.menu = this;
        this.populateMenu();
    }

    @Override
    public void populateMenu() {
        for (final Gizmo gizmo : this.gizmos) {
            this.addItem(new Clickable(gizmo.getItem()) {
                @Override
                @EventHandler
                public void action(InventoryClickEvent event) {
                    if (event.getCurrentItem().equals(gizmo.getItem())) {
                        gizmo.clickAction(menu, event.getActor());
                        event.setCancelled(true);
                    }
                }
            }.getItemStack());
        }
    }

}
