package brcomkassin.dungeonWeapons.weapon;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import org.bukkit.NamespacedKey;

public interface WeaponIds {
    NamespacedKey WEAPON_KEY = new NamespacedKey(DungeonWeaponsPlugin.getInstance(), "weapon_data");
    NamespacedKey WEAPON_ID_KEY = new NamespacedKey(DungeonWeaponsPlugin.getInstance(), "weapon_id");
    NamespacedKey WEAPON_TYPE_KEY = new NamespacedKey(DungeonWeaponsPlugin.getInstance(), "weapon_type");
}


