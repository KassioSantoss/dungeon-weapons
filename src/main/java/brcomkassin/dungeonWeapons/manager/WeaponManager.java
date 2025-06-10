package brcomkassin.dungeonWeapons.manager;

import brcomkassin.dungeonWeapons.*;
import brcomkassin.dungeonWeapons.cache.WeaponCache;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class WeaponManager {

    private final static WeaponManager INSTANCE = new WeaponManager();

    private WeaponManager() {
    }

    public static WeaponManager of() {
        return INSTANCE;
    }

    public Weapon getWeapon(Player player, ItemStack itemStack) {
        WeaponData weaponData = WeaponSerializer.readFromItem(itemStack);

        if (weaponData == null) return null;

        Weapon cached = WeaponCache.getWeaponFromCache(weaponData.getId());
        if (cached != null) return cached;

        WeaponInstance weaponInstance = new WeaponInstance(weaponData);
        WeaponCache.addWeaponToCache(weaponInstance);
        return weaponInstance;
    }

    public Weapon getWeapon(String type) {
        Weapon weapon = WeaponRegistry.getType(type);
        if (weapon == null) {
            ColoredLogger.error("Arma nao encontrada!");
            return null;
        }
        return weapon;
    }

    public Weapon getWeaponByUUID(UUID uuid) {
        return WeaponCache.getWeaponFromCache(uuid);
    }

    public boolean isWeapon(ItemStack itemStack) {
        return PDCUtil.hasPDC(itemStack, WeaponIds.WEAPON_KEY);
    }

}
