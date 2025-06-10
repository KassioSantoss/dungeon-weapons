package brcomkassin.dungeonWeapons;

import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponSerializer {
    private static final Gson GSON = new Gson();

    public static String serialize(Weapon weapon) {
        WeaponData data = new WeaponData(weapon);
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
        ColoredLogger.info("&ALENDO INFORMAÇOES DO ITEM:");
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        String key = PDCUtil.readPDC(item, WeaponIds.WEAPON_ID_KEY);

        if (key == null) return null;

        String json = PDCUtil.readPDC(item, WeaponIds.WEAPON_KEY);
        if (json == null) return null;
        WeaponData deserialize = deserialize(json);
        ColoredLogger.info("&b[readFromItem] deserialize: " + deserialize);
        return deserialize;
    }

}
