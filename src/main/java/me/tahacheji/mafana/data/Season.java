package me.tahacheji.mafana.data;

import java.util.ArrayList;
import java.util.List;

public class Season implements SeasonTriggers {

    private String id;
    private String displayName;
    private int amountOfDays;
    private int priority;
    private List<String> lore;
    private List<SeasonEvent> seasonEvents = new ArrayList<>();

    public Season(String id, String displayName, int amountOfDays, int priority, List<String> lore) {
        this.id = id;
        this.displayName = displayName;
        this.amountOfDays = amountOfDays;
        this.priority = priority;
        this.lore = lore;
    }

    public Season(String id, String displayName, int amountOfDays, int priority, String... lore) {
        this.id = id;
        this.displayName = displayName;
        this.amountOfDays = amountOfDays;
        this.priority = priority;
        this.lore = List.of(lore);
    }


    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAmountOfDays() {
        return amountOfDays;
    }

    public int getPriority() {
        return priority;
    }

    public List<String> getLore() {
        return lore;
    }

    public List<SeasonEvent> getSeasonEvents() {
        return seasonEvents;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAmountOfDays(int amountOfDays) {
        this.amountOfDays = amountOfDays;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setSeasonEvents(List<SeasonEvent> seasonEvents) {
        this.seasonEvents = seasonEvents;
    }
}
