package me.itselliott.gizmos.utils;

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
    };


    public abstract int cost();
    public abstract String string();

}

