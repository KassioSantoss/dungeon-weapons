package brcomkassin.dungeonWeapons.utils.parser;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationParser {

    public static Location from(String locationString) {
        final String[] split = locationString.split(":");
        final String world = split[0];
        return new Location(
                Bukkit.getWorld(world),
                Double.parseDouble(split[1]),
                Double.parseDouble(split[2]),
                Double.parseDouble(split[3])
        );
    }
}
