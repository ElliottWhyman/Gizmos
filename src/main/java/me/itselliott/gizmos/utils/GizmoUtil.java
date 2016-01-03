package me.itselliott.gizmos.utils;

import me.itselliott.gizmos.gizmo.Gizmo;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by Elliott2 on 31/12/2015.
 */
public class GizmoUtil {

    public static HashMap<String, Gizmo> playerGizmos = new HashMap<>();

    public static boolean hasGizmos(Player player) {
        return playerGizmos.containsKey(player.getName());
    }

    public static Gizmo getGizmo(Player player) {
        return playerGizmos.get(player.getName());
    }

    public static void remove(Player player) {
        playerGizmos.remove(player.getName());
    }

}
