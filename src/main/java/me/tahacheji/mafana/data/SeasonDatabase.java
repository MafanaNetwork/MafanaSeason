package me.tahacheji.mafana.data;

import me.TahaCheji.mysqlData.MySQL;
import me.TahaCheji.mysqlData.MysqlValue;
import me.TahaCheji.mysqlData.SQLGetter;
import me.tahacheji.mafana.MafanaSeasons;
import me.tahacheji.mafana.util.EncryptionUtil;

import java.util.UUID;

public class SeasonDatabase extends MySQL {


    public SeasonDatabase() {
        super("localhost", "3306", "51190", "51190", "26c58bbe8e");
    }

    UUID uuid = new EncryptionUtil().stringToUUID("MAFANATION");

    SQLGetter sqlGetter = new SQLGetter(this);

    public int getDay() {
        return sqlGetter.getInt(uuid, new MysqlValue("DAY"));
    }

    public String getSeason() {
        return sqlGetter.getString(uuid, new MysqlValue("SEASON"));
    }

    public void setSeason(String s) {
        sqlGetter.setString(new MysqlValue("SEASON", uuid, s));
    }

    public void setDay(int i) {
        sqlGetter.setInt(new MysqlValue("DAY", uuid, i));
    }

    @Override
    public void connect() {
        super.connect();
        if(this.isConnected())
            sqlGetter.createTable("mafana_seasons",
                    new MysqlValue("SEASON", ""),
                    new MysqlValue("DAY", 0));

        if(!sqlGetter.exists(uuid)) {
            sqlGetter.setString(new MysqlValue("SEASON", uuid, MafanaSeasons.getInstance().getSeasonList().get(0).getId()));
            sqlGetter.setInt(new MysqlValue("DAY", uuid, 0));
            sqlGetter.setUUID(new MysqlValue("SERVER", uuid, uuid));
        }
    }
}
