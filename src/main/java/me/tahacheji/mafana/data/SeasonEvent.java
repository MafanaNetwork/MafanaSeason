package me.tahacheji.mafana.data;

import java.util.List;

public class SeasonEvent implements SeasonTriggers {

    private String id;
    private String displayName;
    private Season season;
    private int amountOfDays;
    private double percentage;
    private List<String> lore;

    public SeasonEvent(String id, String displayName, Season season, int amountOfDays, double percentage, List<String> lore) {
        this.id = id;
        this.displayName = displayName;
        this.season = season;
        this.amountOfDays = amountOfDays;
        this.percentage = percentage;
        this.lore = lore;
    }

    public SeasonEvent(String id, String displayName, Season season, int amountOfDays, double percentage, String... lore) {
        this.id = id;
        this.displayName = displayName;
        this.season = season;
        this.amountOfDays = amountOfDays;
        this.percentage = percentage;
        this.lore = List.of(lore);
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Season getSeason() {
        return season;
    }

    public int getAmountOfDays() {
        return amountOfDays;
    }

    public double getPercentage() {
        return percentage;
    }

    public List<String> getLore() {
        return lore;
    }
}
