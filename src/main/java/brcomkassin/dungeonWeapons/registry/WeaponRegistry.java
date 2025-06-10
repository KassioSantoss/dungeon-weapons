package brcomkassin.dungeonWeapons.registry;

import brcomkassin.dungeonWeapons.*;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import brcomkassin.dungeonWeapons.weapons.BigBertha;
import brcomkassin.dungeonWeapons.weapons.RoyalSword;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class WeaponRegistry {
    private static final Map<String, Supplier<Weapon>> WEAPON_SUPPLIERS = new ConcurrentHashMap<>();

    static {
        register(WeaponType.BIG_BERTHA.name(), BigBertha::new);
        register(WeaponType.ROYAL_SWORD.name(), RoyalSword::new);
    }

    public static void register(String type, Supplier<Weapon> supplier) {
        WEAPON_SUPPLIERS.put(type.toUpperCase(Locale.ROOT), supplier);
    }

    public static Weapon getType(String type) {
        ColoredLogger.info("ïnformaçoes sobre o supplier: " + WEAPON_SUPPLIERS);
        Supplier<Weapon> supplier = WEAPON_SUPPLIERS.get(type.toUpperCase(Locale.ROOT));
        return supplier != null ? supplier.get() : null;
    }

}