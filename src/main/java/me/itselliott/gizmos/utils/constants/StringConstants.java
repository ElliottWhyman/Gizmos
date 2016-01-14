package me.itselliott.gizmos.utils.constants;

/**
 * Created by Elliott2 on 14/01/2016.
 */
public enum StringConstants {

    GIZMOS("Gizmos"),
    CONFIRMATION("Confirmation Menu"),

    RAINDROPS("Raindrops");

    private String string;

    StringConstants(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }
}
