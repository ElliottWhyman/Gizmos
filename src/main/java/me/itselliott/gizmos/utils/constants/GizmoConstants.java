package me.itselliott.gizmos.utils.constants;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Elliott on 04/12/2015.
 */
public enum GizmoConstants {

    RAINDROP_BOMB {
        @Override
        public int cost() {
            return 1000;
        }

        @Override
        public String string() {
            return "Raindrop Bomb";
        }

        @Override
        public List<String> description() {
            return Collections.singletonList("Deploy a Raindrop Explosive!");
        }
    },
    SNOWBALL {
        @Override
        public int cost() {
            return 100;
        }

        @Override
        public String string() {
            return "Snowball Gizmo";
        }

        @Override
        public List<String> description() {
            return Arrays.asList("Turn a player to ice!", ChatColor.RED + "Currently broken");
        }
    },
    PAINT {
        @Override
        public int cost() {
            return 200;
        }

        @Override
        public String string() {
            return "Paint Gizmo";
        }

        @Override
        public List<String> description() {
            return Collections.singletonList("Create a pretty trail behind you!");
        }
    },
    DOWNPOUR {
        @Override
        public int cost() {
            return 100;
        }

        @Override
        public String string() {
            return "Downpour Gizmo";
        }

        @Override
        public List<String> description() {
            return Collections.singletonList("Summon a Raindrop storm above you!");
        }
    };


    public abstract int cost();
    public abstract String string();
    public abstract List<String> description();

}

