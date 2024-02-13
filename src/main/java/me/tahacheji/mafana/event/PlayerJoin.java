package me.tahacheji.mafana.event;

import me.tahacheji.mafana.MafanaSeasons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(MafanaSeasons.getInstance().getSeasonManager().getCurrentSeason() != null) {
            MafanaSeasons.getInstance().getSeasonManager().getCurrentSeason().onPlayerJoin(event.getPlayer().getWorld(), event.getPlayer());
        }
        if(MafanaSeasons.getInstance().getSeasonManager().getSeasonEvent() != null) {
            MafanaSeasons.getInstance().getSeasonManager().getSeasonEvent().onPlayerJoin(event.getPlayer().getWorld(), event.getPlayer());
        }
    }
}
