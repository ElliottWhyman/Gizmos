package me.itselliott.gizmos.utils;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.gizmo.Gizmo;
import me.itselliott.gizmos.inventory.menus.ConfirmationMenu;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

    public static void checkAndBuyGizmo(Player player, int cost, ItemStack itemStack) {
        if (Gizmos.get().getRaindropHandler().canAfford(player.getUniqueId(), cost)) {
            new ConfirmationMenu(Gizmos.get(), cost, itemStack).open(player);
        } else player.sendMessage(ChatColor.RED + "Insufficient Funds");
    }

}
