package me.tahacheji.mafana.data;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.event.MSNextDayEvent;
import me.tahacheji.mafana.event.MSNextSeasonEvent;
import me.tahacheji.mafana.event.MSNextSeasonEventEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class SeasonManager implements Listener {

    private final SeasonDatabase seasonsMysql = MafanaSeasons.getInstance().getSeasonDatabase();

    private final World world;
    private int howLongDayLastInSec;

    private BukkitTask runnable;

    private Season currentSeason = getSeason("");
    private SeasonEvent seasonEvent = null;

    private int daysPassed = 0;
    private int seasonEventDaysPassed = 0;

    private int secondsPassed = 0;

    private boolean paused = false;

    public SeasonManager(World world, int howLongDayLastInSec) {
        this.world = world;
        this.howLongDayLastInSec = howLongDayLastInSec;
        CompletableFuture.supplyAsync(() -> {
           currentSeason = getSeason(seasonsMysql.getSeason().join());
            daysPassed = seasonsMysql.getDay().join();
            return null;
        });
    }

    public void start() {
        long ticksPerMinecraftDay = 24000L;

        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (!paused) {
                    secondsPassed++;
                    if (seasonEvent != null) {
                        seasonEvent.whileThere(world);
                    }
                    currentSeason.whileThere(world);

                    long totalTicks = (secondsPassed * 20L) % (howLongDayLastInSec * 20L);

                    long time = totalTicks * ticksPerMinecraftDay / (howLongDayLastInSec * 20L);

                    Bukkit.getScheduler().runTask(MafanaSeasons.getInstance(), () ->  world.setTime(time));

                    if (secondsPassed >= howLongDayLastInSec) {
                        nextDayEvent();
                    }
                }
            }
        }.runTaskTimerAsynchronously(MafanaSeasons.getInstance(), 0L, 20L);
    }



    public void stop() {
        runnable.cancel();
        MafanaSeasons.getInstance().getSeasonDatabase().setSeason(currentSeason.getId());
        MafanaSeasons.getInstance().getSeasonDatabase().setDay(daysPassed);
    }

    public void nextDayEvent() {
        secondsPassed = 0;
        daysPassed++;
        currentSeason.dayPassed(world);
        Bukkit.getScheduler().runTask(MafanaSeasons.getInstance(), () ->  Bukkit.getPluginManager().callEvent(new MSNextDayEvent(world, currentSeason, seasonEvent, daysPassed)));
        if (seasonEvent != null) {
            seasonEvent.dayPassed(world);
            seasonEventDaysPassed++;
            if (seasonEventDaysPassed >= seasonEvent.getAmountOfDays()) {
                nextSeasonEvent();
            }
        } else {
            seasonEvent = selectEventWithPercentage(currentSeason.getSeasonEvents());
        }
        if (daysPassed >= currentSeason.getAmountOfDays()) {
            nextSeasonEvent();
        }
    }

    public void nextSeasonEventEvent() {
        seasonEventDaysPassed = 0;
        seasonEvent.onDisable(world);
        SeasonEvent oldEvent = seasonEvent;
        seasonEvent = selectEventWithPercentage(currentSeason.getSeasonEvents());
        if (seasonEvent != null) {
            seasonEvent.onEnable(world);
        }
        Bukkit.getScheduler().runTask(MafanaSeasons.getInstance(), () ->  Bukkit.getPluginManager().callEvent(new MSNextSeasonEventEvent(world, currentSeason, currentSeason, seasonEvent, oldEvent, daysPassed)));
    }

    public void nextSeasonEventEvent(SeasonEvent s) {
        seasonEventDaysPassed = 0;
        seasonEvent.onDisable(world);
        SeasonEvent oldEvent = seasonEvent;
        seasonEvent = s;
        if (seasonEvent != null) {
            seasonEvent.onEnable(world);
        }
        Bukkit.getScheduler().runTask(MafanaSeasons.getInstance(), () ->  Bukkit.getPluginManager().callEvent(new MSNextSeasonEventEvent(world, currentSeason, currentSeason, seasonEvent, oldEvent, daysPassed)));
    }

    public void nextSeasonEvent() {
        daysPassed = 0;
        currentSeason.onDisable(world);
        Season oldSeason = currentSeason;
        SeasonEvent oldEvent = seasonEvent;
        if(selectNewSeason() != null) {
            currentSeason = selectNewSeason();
            currentSeason.onEnable(world);
            seasonEvent = selectEventWithPercentage(currentSeason.getSeasonEvents());
        }
        Bukkit.getScheduler().runTask(MafanaSeasons.getInstance(), () ->  Bukkit.getPluginManager().callEvent(new MSNextSeasonEvent(world, currentSeason, oldSeason, seasonEvent, oldEvent, daysPassed)));
    }

    public void nextSeasonEvent(Season season) {
        daysPassed = 0;
        currentSeason.onDisable(world);
        Season oldSeason = currentSeason;
        SeasonEvent oldEvent = seasonEvent;
        if(selectNewSeason() != null) {
            currentSeason = season;
            currentSeason.onEnable(world);
            seasonEvent = selectEventWithPercentage(currentSeason.getSeasonEvents());
        }
        Bukkit.getScheduler().runTask(MafanaSeasons.getInstance(), () ->  Bukkit.getPluginManager().callEvent(new MSNextSeasonEvent(world, currentSeason, oldSeason, seasonEvent, oldEvent, daysPassed)));
    }

    private Season selectNewSeason() {
        List<Season> seasonList = MafanaSeasons.getInstance().getSeasonList();
        Season nextSeason = null;
        int currentPriority = currentSeason.getPriority();

        for (Season season : seasonList) {
            int seasonPriority = season.getPriority();

            if (seasonPriority > currentPriority) {
                if (nextSeason == null || seasonPriority < nextSeason.getPriority()) {
                    nextSeason = season;
                }
            }
        }

        if (nextSeason == null) {
            for (Season season : seasonList) {
                int seasonPriority = season.getPriority();

                if (nextSeason == null || seasonPriority < nextSeason.getPriority()) {
                    nextSeason = season;
                }
            }
        }
        if(nextSeason == null) {
            return currentSeason;
        } else {
            return nextSeason;
        }
    }

    private SeasonEvent selectEventWithPercentage(List<SeasonEvent> events) {
        double totalPercentage = events.stream().mapToDouble(SeasonEvent::getPercentage).sum();

        Random random = new Random();
        double randomValue = random.nextDouble() * totalPercentage;

        double cumulativePercentage = 0;
        for (SeasonEvent event : events) {
            cumulativePercentage += event.getPercentage();
            if (randomValue <= cumulativePercentage) {
                return event;
            }
        }

        return null;
    }

    public void registerListener() {
        MafanaSeasons.getInstance().getServer().getPluginManager().registerEvents(this, MafanaSeasons.getInstance());
    }

    public Season getSeason(String s) {
        return MafanaSeasons.getInstance().getSeason(s);
    }

    public void setCurrentSeason(Season currentSeason) {
        this.currentSeason = currentSeason;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public SeasonDatabase getSeasonsMysql() {
        return seasonsMysql;
    }

    public World getWorld() {
        return world;
    }

    public int getHowLongDayLastInSec() {
        return howLongDayLastInSec;
    }

    public BukkitTask getRunnable() {
        return runnable;
    }

    public Season getCurrentSeason() {
        return currentSeason;
    }

    public SeasonEvent getSeasonEvent() {
        return seasonEvent;
    }

    public int getDaysPassed() {
        return daysPassed;
    }

    public int getSeasonEventDaysPassed() {
        return seasonEventDaysPassed;
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setRunnable(BukkitTask runnable) {
        this.runnable = runnable;
    }

    public void setSeasonEvent(SeasonEvent seasonEvent) {
        this.seasonEvent = seasonEvent;
    }

    public void setDaysPassed(int daysPassed) {
        this.daysPassed = daysPassed;
    }

    public void setSeasonEventDaysPassed(int seasonEventDaysPassed) {
        this.seasonEventDaysPassed = seasonEventDaysPassed;
    }

    public void setSecondsPassed(int secondsPassed) {
        this.secondsPassed = secondsPassed;
    }

    public void setHowLongDayLastInSec(int howLongDayLastInSec) {
        this.howLongDayLastInSec = howLongDayLastInSec;
    }
}
