package brcomkassin.dungeonWeapons.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PDCUtils {

    public static void setJson(ItemMeta meta, NamespacedKey key, JsonObject data) {
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, data.toString());
    }

    public static JsonObject getJson(ItemMeta meta, NamespacedKey key) {
        if (!meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) return null;
        String json = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        assert json != null;
        return JsonParser.parseString(json).getAsJsonObject();
    }

}