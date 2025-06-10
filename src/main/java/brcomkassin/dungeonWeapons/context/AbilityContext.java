package brcomkassin.dungeonWeapons.context;

import brcomkassin.dungeonWeapons.Weapon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public record AbilityContext(Player player, Entity target, Weapon weapon) {

}
