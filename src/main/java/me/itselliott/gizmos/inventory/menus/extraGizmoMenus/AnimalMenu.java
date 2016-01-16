package me.itselliott.gizmos.inventory.menus.extraGizmoMenus;

import me.itselliott.gizmos.gizmo.gizmos.animalGun.Animal;
import me.itselliott.gizmos.gizmo.gizmos.animalGun.AnimalGunGizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.interactables.clickables.Clickable;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Arrays;
import java.util.List;

import static me.itselliott.gizmos.utils.constants.GizmoConstants.ANIMAL_GUN;

/**
 * Created by Elliott2 on 14/01/2016.
 */
public class AnimalMenu extends Menu {

    private List<Animal> animals = Arrays.asList(
            new Animal(EntityType.SHEEP,        200, new ItemBuilder(Material.MONSTER_EGG).setDamage((short) 91).createItem(),  ChatColor.LIGHT_PURPLE + "Sheep"),
            new Animal(EntityType.COW,          200, new ItemBuilder(Material.MONSTER_EGG).setDamage((short) 92).createItem(),  ChatColor.DARK_GRAY + "Cow"),
            new Animal(EntityType.HORSE,        200, new ItemBuilder(Material.MONSTER_EGG).setDamage((short) 100).createItem(), ChatColor.YELLOW + "Horse"),
            new Animal(EntityType.SQUID,        200, new ItemBuilder(Material.MONSTER_EGG).setDamage((short) 94).createItem(),  ChatColor.DARK_BLUE + "Squid"),
            new Animal(EntityType.MUSHROOM_COW, 500, new ItemBuilder(Material.MONSTER_EGG).setDamage((short) 96).createItem(),  ChatColor.RED + "Mooshroom")
    );

    private Menu parent;

    public AnimalMenu(Menu parent) {
        super(ANIMAL_GUN.name(), 7);
        this.parent = parent;
        this.setRows((int) Math.ceil((double) (this.animals.size() + 1) / 9));
        this.populateMenu();
    }


    @Override
    public void populateMenu() {
        for (final Animal animal : this.animals) {
            this.addItem(new Clickable(animal.getItemStack()) {
                @Override
                @EventHandler
                public void action(InventoryClickEvent event) {
                    if (event.getCurrentItem().equals(animal.getItemStack())) {
                        GizmoUtil.checkAndBuyGizmo(event.getActor(), new AnimalGunGizmo(animal), animal.getItemStack());
                        event.setCancelled(true);
                    }
                }
            }.getItemStack());
        }
        /**
         * TODO: Remove this boilerplate code
         */
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
