package me.tahacheji.mafana.season;

import me.tahacheji.mafana.data.Season;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class Spring extends Season {
    public Spring() {
        super("SPRING", ChatColor.GREEN + "☀ Spring", 50, 1,
                "Typically lasts around 30 to 60 in-game days.",
                "Spring is often associated with growth and renewal,",
                "so a moderate duration allows players to experience the transition from winter to warmer weather and see the world come back to life.");
    }

    @Override
    public boolean onEnable(World world) {
        return true;
    }
}
