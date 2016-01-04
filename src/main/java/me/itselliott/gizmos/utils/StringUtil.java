package me.itselliott.gizmos.utils;

import org.bukkit.ChatColor;

/**
 * Created by Elliott2 on 04/01/2016.
 */
public class StringUtil {

    public static boolean checkStrings(String string, String stringToCheck) {
        return ChatColor.stripColor(string).equals(ChatColor.stripColor(stringToCheck));
    }

}
