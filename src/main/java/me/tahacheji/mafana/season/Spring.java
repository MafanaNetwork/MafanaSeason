package me.tahacheji.mafana.season;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.data.Season;
import me.tahacheji.mafana.processor.WorldBiomeSetter;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.concurrent.CompletableFuture;

public class Spring extends Season {
    public Spring() {
        super("SPRING", ChatColor.GREEN + "☀ Spring", 50, 4,
                "Typically lasts around 30 to 60 in-game days.",
                "Spring is often associated with growth and renewal,",
                "so a moderate duration allows players to experience the transition from winter to warmer weather and see the world come back to life.");
    }

    @Override
    public boolean onEnable(World world) {
        WorldBiomeSetter worldBiomeSetter = new WorldBiomeSetter(world, MafanaSeasons.getInstance().getLocationX(), MafanaSeasons.getInstance().getLocationY(), 4, 200, Biome.WARM_OCEAN);
        CompletableFuture<Void> w = worldBiomeSetter.setBiomeAsync();
        w.thenAccept(e -> {
            System.out.print(ChatColor.GREEN + "Updated Biome");
        });
        return true;
    }
}
