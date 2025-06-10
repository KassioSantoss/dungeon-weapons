package brcomkassin.dungeonWeapons.registry;

import brcomkassin.dungeonWeapons.*;
import brcomkassin.dungeonWeapons.weapons.BigBertha;
import brcomkassin.dungeonWeapons.weapons.RoyalSword;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class WeaponRegistry {
    private static final Map<String, Supplier<Weapon>> WEAPON_SUPPLIERS = new ConcurrentHashMap<>();
    private final static Map<String, Weapon> WEAPON_CACHE = new HashMap<>();

    static {
        register(WeaponType.BIG_BERTHA.name(), BigBertha::new);
        register(WeaponType.ROYAL_SWORD.name(), RoyalSword::new);
    }

    public static void register(String type, Supplier<Weapon> supplier) {
        WEAPON_SUPPLIERS.put(type.toUpperCase(Locale.ROOT), supplier);
    }

    public static Weapon fromType(String type) {
        Supplier<Weapon> supplier = WEAPON_SUPPLIERS.get(type.toUpperCase(Locale.ROOT));
        return supplier != null ? supplier.get() : null;
    }

    public static boolean isWeapon(ItemStack item) {
        if (item == null || item.getType().isAir()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(
                WeaponIds.WEAPON_KEY,
                PersistentDataType.STRING
        );
    }

}