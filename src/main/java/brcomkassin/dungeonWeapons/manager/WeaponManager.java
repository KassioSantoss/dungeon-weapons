package brcomkassin.dungeonWeapons.manager;

import brcomkassin.dungeonWeapons.*;
import brcomkassin.dungeonWeapons.cache.WeaponCache;
import brcomkassin.dungeonWeapons.data.WeaponData;
import brcomkassin.dungeonWeapons.data.WeaponDataSerializer;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class WeaponManager {

    private final static WeaponManager INSTANCE = new WeaponManager();

    private WeaponManager() {
    }

    public static WeaponManager of() {
        return INSTANCE;
    }

    public Weapon getWeapon(Player player, ItemStack item) {
        Map<String, Weapon> cache = WeaponCache.getWeaponCache();
        String weaponId = getWeaponId(item);
        if (cache.containsKey(weaponId)) return cache.get(weaponId);

        Weapon weapon = getWeaponFromItem(item);

        if (weapon != null) {
            cache.put(weaponId, weapon);
            weapon.saveWeaponStateToItem(player);
        }
        return weapon;
    }

    public Weapon getWeaponFromItem(ItemStack item) {
        String id = INSTANCE.getWeaponId(item);
        if (id == null) return null;
        WeaponData data = WeaponDataSerializer.readFromItem(item);
        if (data == null || data.id == null || data.type == null) return null;
        Weapon weapon = WeaponRegistry.fromType(data.type);
        if (weapon == null) return null;

        ColoredLogger.info("[getWeaponFromItem]: " + data);

        weapon.initWeaponState(data);
        return weapon;
    }

    public boolean isWeapon(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() == Material.AIR) return false;
        ItemMeta meta = itemStack.getItemMeta();

        if (meta == null) return false;

        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(WeaponIds.WEAPON_KEY, PersistentDataType.STRING);
    }

    public ItemStack saveDataToItem(ItemStack item, WeaponData data) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        String json = WeaponDataSerializer.serialize(data);
        meta.getPersistentDataContainer().set(WeaponIds.WEAPON_KEY, PersistentDataType.STRING, json);
        item.setItemMeta(meta);
        return item;
    }

    public WeaponData readDataFromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        String json = meta.getPersistentDataContainer().get(WeaponIds.WEAPON_KEY, PersistentDataType.STRING);
        if (json == null) return null;
        return WeaponDataSerializer.deserialize(json);
    }

    public String getWeaponId(ItemStack item) {
        WeaponData data = readDataFromItem(item);
        return data != null ? data.id : null;
    }

    public Weapon getWeaponById(WeaponType type) {
        return WeaponRegistry.fromType(type.name());
    }

}
