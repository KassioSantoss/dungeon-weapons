package brcomkassin.dungeonWeapons.cache;

import brcomkassin.dungeonWeapons.Weapon;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WeaponCache {

    private final static Map<UUID, Weapon> WEAPON_CACHE = new HashMap<>();

    public static void addWeaponToCache(Weapon weapon) {
        WEAPON_CACHE.put(weapon.getId(), weapon);
    }

    public static void removeWeaponFromCache(UUID id) {
        WEAPON_CACHE.remove(id);
    }

    public static Weapon getWeaponFromCache(UUID id) {
        if (!WEAPON_CACHE.containsKey(id)) return null;
        return WEAPON_CACHE.get(id);
    }

}
