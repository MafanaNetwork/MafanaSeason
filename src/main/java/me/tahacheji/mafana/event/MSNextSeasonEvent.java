package me.tahacheji.mafana.event;

import me.tahacheji.mafana.data.Season;
import me.tahacheji.mafana.data.SeasonEvent;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MSNextSeasonEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final World world;
    private final Season newSeason;
    private final Season oldSeason;
    private final SeasonEvent newSeasonEvent;
    private final SeasonEvent oldSeasonEvent;
    private final int day;

    public MSNextSeasonEvent(World world, Season newSeason, Season oldSeason, SeasonEvent newSeasonEvent, SeasonEvent oldSeasonEvent, int day) {
        this.world = world;
        this.newSeason = newSeason;
        this.oldSeason = oldSeason;
        this.newSeasonEvent = newSeasonEvent;
        this.oldSeasonEvent = oldSeasonEvent;
        this.day = day;
    }

    public World getWorld() {
        return world;
    }

    public Season getNewSeason() {
        return newSeason;
    }

    public Season getOldSeason() {
        return oldSeason;
    }

    public SeasonEvent getNewSeasonEvent() {
        return newSeasonEvent;
    }

    public SeasonEvent getOldSeasonEvent() {
        return oldSeasonEvent;
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
