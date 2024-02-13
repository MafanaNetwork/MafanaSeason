package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.commandExecutor.Command;
import me.tahacheji.mafana.commandExecutor.paramter.Param;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AdminCommand {

    @Command(names = "ms skipDay", permission = "mafana.admin")
    public void skipDay(Player player, @Param(name = "amountOfDays", required = false) int amount) {
        for(int i = 0; i<amount;i++) {
            MafanaSeasons.getInstance().getSeasonManager().nextDayEvent();
        }
        player.sendMessage(ChatColor.GREEN + "Skipped Days!");
    }

    @Command(names = "ms skipSeason", permission = "mafana.admin")
    public void skipSeason(Player player) {
        MafanaSeasons.getInstance().getSeasonManager().nextSeasonEvent();
        player.sendMessage(ChatColor.GREEN + "Skipped Season!");
    }

    @Command(names = "ms skipEvent", permission = "mafana.admin")
    public void skipEvent(Player player) {
        MafanaSeasons.getInstance().getSeasonManager().nextSeasonEventEvent();
        player.sendMessage(ChatColor.GREEN + "Skipped Event!");
    }

    @Command(names = "ms pauseTime", permission = "mafana.admin")
    public void pauseTime(Player player) {
        MafanaSeasons.getInstance().getSeasonManager().setPaused(true);
        player.sendMessage(ChatColor.GREEN + "Paused Time!");
    }

    @Command(names = "ms unPauseTime", permission = "mafana.admin")
    public void unPauseTime(Player player) {
        MafanaSeasons.getInstance().getSeasonManager().setPaused(false);
        player.sendMessage(ChatColor.GREEN + "UnPaused Time!");
    }

    @Command(names = "ms setHowLongPerDayInSeconds", permission = "mafana.admin")
    public void setHowLongPerDayInSeconds(Player player, @Param(name = "seconds") int seconds) {
        MafanaSeasons.getInstance().getSeasonManager().setHowLongDayLastInSec(seconds);
        player.sendMessage(ChatColor.GREEN + "Set How Long Per Day In Seconds!");
    }
}
