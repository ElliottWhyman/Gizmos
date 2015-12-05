package me.itselliott.gizmos.raindrops;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Elliott on 05/12/2015.
 */
public class RaindropHandler {

    private HashMap<UUID, Integer> raindrops; // Where the amount of raindrops each player is stored

    public RaindropHandler() {
        this.raindrops = new HashMap<>();
    }

    public HashMap<UUID, Integer> getRaindrops() {
        return raindrops;
    }

    public int getRaindrops(UUID player) {
        return this.raindrops.get(player);
    }

    public void setRaindrops(UUID player, int value) {
        this.raindrops.put(player, value);
    }

    public boolean canAfford(UUID player, int value) {
        return this.raindrops.get(player) - value >= 0;
    }

}
