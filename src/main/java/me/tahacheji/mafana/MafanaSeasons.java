package me.tahacheji.mafana;

import me.tahacheji.mafana.command.AdminCommand;
import me.tahacheji.mafana.command.PlayerCommand;
import me.tahacheji.mafana.commandExecutor.CommandHandler;
import me.tahacheji.mafana.data.Season;
import me.tahacheji.mafana.data.SeasonDatabase;
import me.tahacheji.mafana.data.SeasonEvent;
import me.tahacheji.mafana.data.SeasonManager;
import me.tahacheji.mafana.event.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class MafanaSeasons extends JavaPlugin {

    private static MafanaSeasons instance;
    private List<Season> seasonList = new ArrayList<>();
    private List<SeasonEvent> seasonEvents = new ArrayList<>();
    private SeasonDatabase seasonDatabase;
    private SeasonManager seasonManager;

    private Location locationX = new Location(Bukkit.getWorld("world"), 2584,136,3373);
    private Location locationY = new Location(Bukkit.getWorld("world"), 2059,-63,2844);

    @Override
    public void onEnable() {
        instance = this;
        String packageName = getClass().getPackage().getName();
        for (Class<?> clazz : new Reflections(packageName, ".events").getSubTypesOf(SeasonEvent.class)) {
            try {
                SeasonEvent getClass = (SeasonEvent) clazz.getDeclaredConstructor().newInstance();
                seasonEvents.add(getClass);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {}
        }
        for (Class<?> clazz : new Reflections(packageName, ".events").getSubTypesOf(Season.class)) {
            try {
                Season getClass = (Season) clazz.getDeclaredConstructor().newInstance();
                getClass.setSeasonEvents(seasonEvents.stream().filter(seasonEvent -> seasonEvent.getSeason().equals(getClass)).collect(Collectors.toList()));
                seasonList.add(getClass);
            } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ignored) {}
        }
        seasonDatabase = new SeasonDatabase();
        seasonDatabase.connect();
        seasonManager = new SeasonManager(Bukkit.getWorld("world"), 600);
        seasonManager.registerListener();
        seasonManager.start();
        CommandHandler.registerCommands(PlayerCommand.class);
        CommandHandler.registerCommands(AdminCommand.class);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        seasonManager.stop();
        seasonDatabase.disconnect();
    }

    public SeasonManager getSeasonManager() {
        return seasonManager;
    }

    public Season getSeason(String id) {
        for(Season s : getSeasonList()) {
            if(s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public SeasonDatabase getSeasonDatabase() {
        return seasonDatabase;
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public List<SeasonEvent> getSeasonEvents() {
        return seasonEvents;
    }

    public Location getLocationX() {
        return locationX;
    }

    public Location getLocationY() {
        return locationY;
    }

    public static MafanaSeasons getInstance() {
        return instance;
    }
}
