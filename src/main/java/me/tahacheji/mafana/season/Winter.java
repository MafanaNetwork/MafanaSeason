package me.tahacheji.mafana.season;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.data.Season;
import me.tahacheji.mafana.processor.WorldBiomeSetter;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Biome;

import java.util.concurrent.CompletableFuture;

public class Winter extends Season {
    public Winter() {
        super("WINTER", ChatColor.AQUA + "❄ Winter", 30, 3,
                "Often the shortest season, lasting around 20 to 40 in-game days.",
                "Winter brings cold weather and snow,",
                " presenting unique challenges such as survival in harsh conditions and limited resources.");
    }

    @Override
    public boolean onEnable(World world) {
        WorldBiomeSetter worldBiomeSetter = new WorldBiomeSetter(world, MafanaSeasons.getInstance().getLocationX(), MafanaSeasons.getInstance().getLocationY(), 4, 200, Biome.SNOWY_TAIGA);
        CompletableFuture<Void> w = worldBiomeSetter.setBiomeAsync();
        w.thenAccept(e -> {
            System.out.print(ChatColor.GREEN + "Updated Biome");
        });
        return true;
    }
}
