package brcomkassin.dungeonWeapons.cache;

import brcomkassin.dungeonWeapons.Weapon;

import java.util.HashMap;
import java.util.Map;

public class WeaponCache {

    private final static Map<String, Weapon> WEAPON_CACHE = new HashMap<>();

    public static void addWeaponToCache(Weapon weapon) {
        WEAPON_CACHE.put(weapon.getId(), weapon);
    }

    public static void removeWeaponFromCache(String id) {
        WEAPON_CACHE.remove(id);
    }

    public static Map<String, Weapon> getWeaponCache() {
        return WEAPON_CACHE;
    }

}
