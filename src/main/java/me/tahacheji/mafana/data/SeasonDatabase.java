package me.tahacheji.mafana.data;

import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.util.EncryptionUtil;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class SeasonDatabase extends MySQL {


    public SeasonDatabase() {
        super("localhost", "3306", "51190", "51190", "26c58bbe8e");
    }

    UUID uuid = new EncryptionUtil().stringToUUID("MAFANATION");

    SQLGetter sqlGetter = new SQLGetter(this);

    public CompletableFuture<Integer> getDay() {
        return sqlGetter.getIntAsync(uuid, new DatabaseValue("DAY"));
    }

    public CompletableFuture<String> getSeason() {
        return sqlGetter.getStringAsync(uuid, new DatabaseValue("SEASON"));
    }

    public CompletableFuture<Void> setSeason(String s) {
        return sqlGetter.setStringAsync(new DatabaseValue("SEASON", uuid, s));
    }

    public CompletableFuture<Void> setDay(int i) {
        return sqlGetter.setIntAsync(new DatabaseValue("DAY", uuid, i));
    }

    public CompletableFuture<Void> connect() {
        return CompletableFuture.supplyAsync(() -> {
            sqlGetter.createTable("mafana_seasons",
                    new DatabaseValue("SEASON", ""),
                    new DatabaseValue("DAY", 0));

            if(!sqlGetter.existsAsync(uuid).join()) {
                return sqlGetter.setStringAsync(new DatabaseValue("SEASON", uuid, MafanaSeasons.getInstance().getSeasonList().get(0).getId()))
                        .thenCompose(__ -> sqlGetter.setIntAsync(new DatabaseValue("DAY", uuid, 0)))
                        .thenCompose(__ -> sqlGetter.setUUIDAsync(new DatabaseValue("SERVER", uuid, uuid))).join();
            }
            return null;
        });
    }
}
