package brcomkassin.dungeonWeapons.registry;

import brcomkassin.dungeonWeapons.utils.KPDCUtil;
import brcomkassin.dungeonWeapons.weapon.WeaponIds;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Supplier;
public final class WeaponRegistry {

    private static final Map<String, Supplier<Weapon>> REGISTERED_WEAPONS = new HashMap<>();

    private WeaponRegistry() {}

    public static void register(String name, Supplier<Weapon> supplier) {
        if (REGISTERED_WEAPONS.containsKey(name.toLowerCase())) {
            throw new IllegalArgumentException("WeaponType '" + name + "' already registered.");
        }
        REGISTERED_WEAPONS.put(name.toLowerCase(), supplier);
    }

    public static Weapon create(String name) {
        Supplier<Weapon> supplier = REGISTERED_WEAPONS.get(name.toLowerCase());
        return supplier != null ? supplier.get() : null;
    }

    public static Weapon fromItem(ItemStack item) {
        if (item == null) return null;
        String type = KPDCUtil.readPDC(item, WeaponIds.WEAPON_TYPE_KEY);
        return type != null ? create(type) : null;
    }

    public static Set<String> getRegisteredTypes() {
        return Collections.unmodifiableSet(REGISTERED_WEAPONS.keySet());
    }
}
