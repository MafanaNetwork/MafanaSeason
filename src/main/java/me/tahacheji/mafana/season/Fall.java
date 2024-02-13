package me.tahacheji.mafana.season;

import me.tahacheji.mafana.data.Season;
import org.bukkit.ChatColor;
import org.bukkit.World;

public class Summer extends Season {
    public Summer() {
        super("SUMMER", ChatColor.YELLOW + "☼ Summer", 60, 1,
                "Usually lasts longer than spring, spanning around 60 to 90 in-game days.",
                "Summer represents the peak of warmth and activity, providing players with ample time to enjoy outdoor activities and explore the world in its full splendor.");
    }

    @Override
    public boolean onEnable(World world) {
        return true;
    }
}
