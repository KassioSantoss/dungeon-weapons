package brcomkassin.dungeonWeapons.manager;

import brcomkassin.dungeonWeapons.cache.WeaponCache;
import brcomkassin.dungeonWeapons.utils.KPDCUtil;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponIds;
import brcomkassin.dungeonWeapons.weapon.data.WeaponInstance;
import brcomkassin.dungeonWeapons.weapon.data.WeaponData;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import brcomkassin.dungeonWeapons.weapon.data.WeaponSerializer;
import org.bukkit.inventory.ItemStack;

public class WeaponManager {

    private final static WeaponManager INSTANCE = new WeaponManager();

    private WeaponManager() {
    }

    public static WeaponManager of() {
        return INSTANCE;
    }

    public Weapon getWeapon(ItemStack itemStack) {
        if (!isWeapon(itemStack)) return null;

        WeaponData weaponData = WeaponSerializer.readFromItem(itemStack);

        if (weaponData == null) return null;

        Weapon cached = WeaponCache.getWeaponFromCache(weaponData.getId());

        if (cached != null) return cached;

        WeaponInstance weaponInstance = new WeaponInstance(weaponData);
        WeaponCache.addWeaponToCache(weaponInstance);
        return weaponInstance;
    }

    public Weapon getWeapon(String type) {
        Weapon weapon = WeaponRegistry.create(type);
        return weapon;
    }

    public boolean isWeapon(ItemStack itemStack) {
        return KPDCUtil.hasPDC(itemStack, WeaponIds.WEAPON_KEY);
    }

}
