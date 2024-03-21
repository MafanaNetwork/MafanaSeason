package me.tahacheji.mafana.season;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.data.Season;
import me.tahacheji.mafana.processor.WorldBiomeSetter;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.concurrent.CompletableFuture;

public class Fall extends Season {
    public Fall() {
        super("FALL", ChatColor.DARK_PURPLE + "☁ Fall", 40, 2,
                "Similar to spring, fall typically lasts around 30 to 60 in-game days.",
                "Fall is characterized by changing colors and the transition from warmth to cooler temperatures, leading into winter.");
    }

    @Override
    public boolean onEnable(World world) {
        WorldBiomeSetter worldBiomeSetter = new WorldBiomeSetter(world, MafanaSeasons.getInstance().getLocationX(), MafanaSeasons.getInstance().getLocationY(), 3, 200, Biome.PLAINS);
        CompletableFuture<Void> w = worldBiomeSetter.setBiomeAsync();
        w.thenAccept(e -> System.out.print(ChatColor.GREEN + "Updated Biome"));
        return true;
    }
}
