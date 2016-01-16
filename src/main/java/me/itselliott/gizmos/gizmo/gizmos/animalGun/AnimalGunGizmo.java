package me.itselliott.gizmos.gizmo.gizmos.animalGun;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.event.gizmo.GizmoUseEvent;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.Menu;
import me.itselliott.gizmos.inventory.menus.extraGizmoMenus.AnimalMenu;
import me.itselliott.gizmos.utils.GizmoUtil;
import me.itselliott.gizmos.utils.ItemBuilder;
import me.itselliott.gizmos.utils.Time;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.UUID;

import static me.itselliott.gizmos.utils.constants.GizmoConstants.ANIMAL_GUN;

/**
 * This gizmo needs a lot of tidying up - currently doesn't look very good.
 *
 * Created by Elliott2 on 14/01/2016.
 */
public class AnimalGunGizmo extends Gizmo {

    private Animal animal;
    private UUID animalId;
    private Player player;

    private int taskId;

    public AnimalGunGizmo() {
        super(ANIMAL_GUN, new ItemBuilder(Material.MONSTER_EGG).setName(ChatColor.LIGHT_PURPLE + ANIMAL_GUN.string()).createItem());
        this.animal = new Animal(EntityType.SHEEP, ANIMAL_GUN.cost(), new ItemBuilder(Material.MONSTER_EGG).createItem(), ChatColor.LIGHT_PURPLE + ANIMAL_GUN.string());
        this.animalId = UUID.randomUUID();
        Gizmos.get().registerListener(this);
    }

    public AnimalGunGizmo(Animal animal) {
        super(ANIMAL_GUN.name(), ANIMAL_GUN.cost() + animal.getCost(), ANIMAL_GUN.description(), new ItemBuilder(Material.STAINED_CLAY).setName(ChatColor.RED + ANIMAL_GUN.string()).createItem());
        this.animal = animal;
        this.animalId = UUID.randomUUID();
        Gizmos.get().registerListener(this);

    }

    @EventHandler
    public void onGizmoUse(GizmoUseEvent event) {
        if (!event.isCancelled() && event.getGizmo() == this) {
            this.player = event.getPlayer();
            this.shootAnimal(event.getPlayer());
            this.removeAnimal(event.getLocation());
        }
    }

    private void shootAnimal(Player player) {
        Entity entity = player.getLocation().getWorld().spawnEntity(player.getLocation(), animal.getAnimal());
        entity.setCustomName(player.getDisplayName() + "'s flying " + this.animal.getName());
        entity.setVelocity(player.getLocation().getDirection().normalize().multiply(4));
        entity.setMetadata("animal-gun", new FixedMetadataValue(Gizmos.get(), this.animalId.toString()));
        this.showTrail(entity);
    }

    private void showTrail(final Entity animal) {
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gizmos.get(), new Runnable() {
            @Override
            public void run() {
                if (!animal.isOnGround()) {
                    Firework firework = animal.getLocation().getWorld().spawn(animal.getLocation(), Firework.class);
                    FireworkMeta fireworkMeta = firework.getFireworkMeta();
                    fireworkMeta.addEffects(FireworkEffect.builder().withColor(Color.RED, Color.WHITE).with(FireworkEffect.Type.BALL_LARGE).build());
                    fireworkMeta.setPower(0);
                    ((CraftFirework) firework).getHandle().expectedLifespan = 1;
                    firework.setFireworkMeta(fireworkMeta);
                }
            }
        },0, 1);
    }

    private void cancelTask() {
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

    private void removeAnimal(final Location location) {
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Gizmos.get(), new Runnable() {
            @Override
            public void run() {
                for (Entity entity : location.getWorld().getEntities()) {
                    if (entity.hasMetadata("animal-gun")) {
                        for (MetadataValue value : entity.getMetadata("animal-gun")) {
                            if (value.value().toString().equals(animalId.toString()) && entity.isOnGround()) {
                                entity.remove();
                                GizmoUtil.remove(player);
                                cancelTask();
                            }
                        }
                    }
                }
            }
        },0, Time.toTicks(1));

    }

    @Override
    public void clickAction(Menu menu, Player player) {
        new AnimalMenu(menu).open(player);
    }

}
