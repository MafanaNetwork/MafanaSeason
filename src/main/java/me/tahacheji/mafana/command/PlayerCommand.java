package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.commandExecutor.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerCommand {

    @Command(names = "ms info", permission = "mafana.copper")
    public void seasonInfo(Player player) {
        player.sendMessage(ChatColor.YELLOW + "The Current Season Is: " + MafanaSeasons.getInstance().getSeasonManager().getCurrentSeason().getDisplayName());
        player.sendMessage(ChatColor.YELLOW + "The Current Event Is: " + MafanaSeasons.getInstance().getSeasonManager().getSeasonEvent().getDisplayName());
        player.sendMessage(ChatColor.YELLOW + "The Current Day Is: " + MafanaSeasons.getInstance().getSeasonManager().getDaysPassed());
    }
}
