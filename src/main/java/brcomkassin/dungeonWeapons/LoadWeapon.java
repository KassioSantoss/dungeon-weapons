package brcomkassin.dungeonWeapons;

import org.bukkit.inventory.ItemStack;

public class LoadWeapon {

    public static void load(ItemStack item) {
        WeaponData weaponData = WeaponSerializer.readFromItem(item);




    }

}
