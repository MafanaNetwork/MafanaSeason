package me.tahacheji.mafana.season;

import me.tahacheji.mafana.data.Season;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class Fall extends Season {
    public Fall() {
        super("FALL", ChatColor.DARK_PURPLE + "☁ Fall", 40, 1,
                "Similar to spring, fall typically lasts around 30 to 60 in-game days.",
                "Fall is characterized by changing colors and the transition from warmth to cooler temperatures, leading into winter.");
    }

    @Override
    public boolean onEnable(World world) {
        return true;
    }
}
