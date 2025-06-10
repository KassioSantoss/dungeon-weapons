package brcomkassin.dungeonWeapons.weapon;

import brcomkassin.dungeonWeapons.utils.PDCUtil;
import brcomkassin.dungeonWeapons.weapon.item.BigBertha;
import brcomkassin.dungeonWeapons.weapon.item.RoyalSword;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public enum WeaponType {
    BIG_BERTHA(BigBertha::new), ROYAL_SWORD(RoyalSword::new);

    private final Supplier<Weapon> weapon;

    WeaponType(Supplier<Weapon> weapon) {
        this.weapon = weapon;
    }

    public static WeaponType fromType(String type) {
        for (WeaponType weaponType : values()) {
            if (weaponType.name().equalsIgnoreCase(type)) return weaponType;
        }
        return null;
    }
    public static Weapon fromItem(ItemStack item) {
        if (item == null) return null;

        String type = PDCUtil.readPDC(item, WeaponIds.WEAPON_TYPE_KEY);

        if (type == null) return null;

        WeaponType weaponType = WeaponType.fromType(type);
        if (weaponType == null) return null;

        return weaponType.getWeapon();
    }

    public Weapon getWeapon() {
        return weapon.get();
    }
}
