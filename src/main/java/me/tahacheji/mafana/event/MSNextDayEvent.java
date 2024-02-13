package me.tahacheji.mafana.event;

import me.tahacheji.mafana.data.Season;
import me.tahacheji.mafana.data.SeasonEvent;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MSNextDayEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final World world;
    private final Season season;
    private final SeasonEvent seasonEvent;
    private final int day;

    public MSNextDayEvent(World world, Season season, SeasonEvent seasonEvent, int day) {
        this.world = world;
        this.season = season;
        this.seasonEvent = seasonEvent;
        this.day = day;
    }

    public World getWorld() {
        return world;
    }

    public Season getSeason() {
        return season;
    }

    public SeasonEvent getSeasonEvent() {
        return seasonEvent;
    }

    public int getDay() {
        return day;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
