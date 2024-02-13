package me.tahacheji.mafana.data;

import org.bukkit.World;
import org.bukkit.entity.Player;

public interface SeasonTriggers {

    default boolean onPlayerJoin(World world, Player player) {return false;}

    default boolean onEnable(World world) {return false;}
    default boolean dayPassed(World world) {return false;}
    default boolean whileThere(World world) {return false;}
    default boolean onDisable(World world) {return false;}
}
