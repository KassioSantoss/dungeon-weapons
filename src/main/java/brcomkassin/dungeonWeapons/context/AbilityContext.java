package brcomkassin.dungeonWeapons.context;

import brcomkassin.dungeonWeapons.weapon.Weapon;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public record AbilityContext(Player player, Entity target, Weapon weapon,Location location) {

}
