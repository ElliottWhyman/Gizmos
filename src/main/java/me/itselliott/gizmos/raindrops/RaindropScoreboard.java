package me.itselliott.gizmos.raindrops;

import me.itselliott.gizmos.Gizmos;
import me.itselliott.gizmos.utils.Constants;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

/**
 * Created by Elliott on 05/12/2015.
 */
public class RaindropScoreboard {

    private ScoreboardManager manager;
    private Scoreboard scoreboard;
    private Objective objective;
    private Player player;

    public RaindropScoreboard(Player player, int raindrops) {
        this.player = player;
        this.manager = Bukkit.getScoreboardManager();
        this.scoreboard = this.manager.getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective("raindrops", "dummy");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.objective.setDisplayName(ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + Constants.RAINDROPS);
        Score score = this.objective.getScore(ChatColor.AQUA + Constants.RAINDROPS);
        score.setScore(raindrops);
    }

    public void update() {
        this.objective.getScore(ChatColor.AQUA + Constants.RAINDROPS).setScore(Gizmos.get().getRaindropHandler().getRaindrops(this.player.getUniqueId()));
        this.player.setScoreboard(this.scoreboard);
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }
}
