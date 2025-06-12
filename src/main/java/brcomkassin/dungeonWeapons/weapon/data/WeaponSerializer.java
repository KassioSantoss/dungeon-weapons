package brcomkassin.dungeonWeapons.weapon.data;

import brcomkassin.dungeonWeapons.utils.KPDCUtil;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponIds;
import brcomkassin.dungeonWeapons.utils.KComponentAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponSerializer {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Component.class, KComponentAdapter.INSTANCE)
            .create();

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
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        String key = KPDCUtil.readPDC(item, WeaponIds.WEAPON_ID_KEY);

        if (key == null) return null;

        String json = KPDCUtil.readPDC(item, WeaponIds.WEAPON_KEY);
        if (json == null) return null;
        WeaponData deserialize = deserialize(json);
        return deserialize;
    }

}
