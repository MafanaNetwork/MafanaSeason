package me.tahacheji.mafana.season;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.data.Season;
import me.tahacheji.mafana.processor.WorldBiomeSetter;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.concurrent.CompletableFuture;

public class Summer extends Season {
    public Summer() {
        super("SUMMER", ChatColor.YELLOW + "☼ Summer", 60, 1,
                "Usually lasts longer than spring, spanning around 60 to 90 in-game days.",
                "Summer represents the peak of warmth and activity, providing players with ample time to enjoy outdoor activities and explore the world in its full splendor.");
    }

    @Override
    public boolean onEnable(World world) {
        WorldBiomeSetter worldBiomeSetter = new WorldBiomeSetter(world, MafanaSeasons.getInstance().getLocationX(), MafanaSeasons.getInstance().getLocationY(), 3, 200, Biome.WINDSWEPT_HILLS);
        CompletableFuture<Void> w = worldBiomeSetter.setBiomeAsync();
        w.thenAccept(e -> {
            System.out.print(ChatColor.GREEN + "Updated Biome");
        });
        return true;
    }
}
