package brcomkassin.dungeonWeapons.data;

import brcomkassin.dungeonWeapons.WeaponIds;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class WeaponDataSerializer {
    private static final Gson GSON = new Gson();

    public static String serialize(WeaponData data) {
        return GSON.toJson(data);
    }

    public static WeaponData deserialize(String json) {
        try {
            return GSON.fromJson(json, WeaponData.class);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Failed to deserialize WeaponData: " + json, e);
        }
    }

    public static WeaponData readFromItem(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        NamespacedKey key = WeaponIds.WEAPON_KEY;
        String json = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);

        if (json == null) return null;
        WeaponData deserialize = WeaponDataSerializer.deserialize(json);

        return deserialize;
    }

}
