package me.itselliott.gizmos.utils;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

/**
 * Created by Elliott on 04/12/2015.
 */
public class Hologram {

    private ArmorStand armorStand;
    private String text;

    public Hologram(Location location, String text) {
        this.armorStand = location.getWorld().spawn(location, ArmorStand.class);
        this.text = text;
        this.armorStand.setVisible(false);
        this.armorStand.setCustomName(text);
        this.armorStand.setCustomNameVisible(true);

    }

    public void setText(String text) {
        this.text = text;
        this.armorStand.setCustomName(text);
    }

    public void remove() {
        this.armorStand.remove();
    }



}
