package brcomkassin.dungeonWeapons.manager;

import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.WeaponIds;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Set;

public class WeaponManager {

    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return false;

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer container = meta.getPersistentDataContainer();

        boolean hasWeaponId = container.has(WeaponIds.WEAPON_KEY, PersistentDataType.STRING);

        if (hasWeaponId) {
            String fullId = container.get(WeaponIds.WEAPON_KEY, PersistentDataType.STRING);
            ColoredLogger.info("&aWeapon ID found: " + fullId);
        } else {
            ColoredLogger.info("&cNo Weapon ID found.");
        }
        return hasWeaponId;
    }


    public Weapon getWeapon(ItemStack itemStack) {
        return WeaponRegistry.getWeapon(itemStack.getItemMeta().getPersistentDataContainer().get(WeaponIds.WEAPON_KEY, PersistentDataType.STRING));
    }

    public Weapon createWeaponByID(String id) {
        return WeaponRegistry.getWeapon(id);
    }

}
