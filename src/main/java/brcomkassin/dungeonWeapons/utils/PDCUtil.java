package brcomkassin.dungeonWeapons.utils;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PDCUtil {

    public static ItemStack savePDC(ItemStack itemStack, NamespacedKey key, String value) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static String readPDC(ItemStack itemStack, NamespacedKey key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return null;
        return meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
    }

    public static void removePDC(ItemStack itemStack, NamespacedKey key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return;
        meta.getPersistentDataContainer().remove(key);
        itemStack.setItemMeta(meta);
    }

    public static boolean hasPDC(ItemStack itemStack, NamespacedKey key) {
        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) return false;
        return meta.getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

}
