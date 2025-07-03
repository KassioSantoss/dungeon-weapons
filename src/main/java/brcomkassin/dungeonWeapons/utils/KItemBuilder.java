package brcomkassin.dungeonWeapons.utils;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class KItemBuilder {

    private final JavaPlugin plugin = DungeonWeaponsPlugin.getInstance();

    private final ItemStack itemStack;

    public KItemBuilder() {
        this.itemStack = new ItemStack(Material.AIR);
    }

    public KItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public KItemBuilder(Material material, short data) {
        this.itemStack = new ItemStack(material, 1, data);
    }

    public KItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static KItemBuilder of() {
        return new KItemBuilder();
    }

    public static KItemBuilder of(ItemStack itemStack) {
        return new KItemBuilder(itemStack);
    }

    public static KItemBuilder of(Material material) {
        return new KItemBuilder(material);
    }

    public static KItemBuilder of(Material material, short data) {
        return new KItemBuilder(material, data);
    }

    private KItemBuilder consumeMeta(Consumer<ItemMeta> consumer) {
        ItemMeta meta = itemStack.getItemMeta();
        consumer.accept(meta);
        itemStack.setItemMeta(meta);
        return this;
    }

    public KItemBuilder consume(Consumer<ItemStack> consumer) {
        consumer.accept(itemStack);

        return this;
    }

    public KItemBuilder setName(Component component) {
        return consumeMeta(meta -> meta.displayName(component));
    }

    private String translateColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public KItemBuilder setName(String displayName) {
        return consumeMeta(meta -> meta.setDisplayName(translateColorCodes(displayName)));
    }

    public KItemBuilder setLore(List<String> lines) {
        return consumeMeta(meta -> meta.setLore(lines.stream().map(this::translateColorCodes).collect(Collectors.toList())));
    }
    public KItemBuilder setLore(Component... lines) {
        return consumeMeta(meta -> meta.lore(List.of(lines)));
    }

    public KItemBuilder setLore(String... lines) {
        return setLore(Arrays.asList(lines));
    }

    public KItemBuilder setAmount(int amount) {
        return consume(item -> item.setAmount(amount));
    }

    public KItemBuilder setType(Material material) {
        return consume(item -> item.setType(material));
    }


    public KItemBuilder addEnchantment(Enchantment enchantment, int level) {
        return consume(item -> item.addUnsafeEnchantment(enchantment, level));
    }

    public KItemBuilder setNameSpacedKey(String id, String key) {
        return consumeMeta(meta -> {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
            container.set(namespacedKey, PersistentDataType.STRING, key);
        });
    }

    public <T, Z> KItemBuilder setNamespacedData(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        return consumeMeta(meta -> {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            container.set(key, type, value);
        });
    }

    public KItemBuilder setItemModel(NamespacedKey namespacedKey) {
        return consumeMeta(meta -> meta.setItemModel(namespacedKey));
    }

    public KItemBuilder setNamespacedKeyById(String id) {
        return consumeMeta(meta -> {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin, "tool_id");
            container.set(key, PersistentDataType.STRING, id);
        });
    }

    public KItemBuilder setCustomModelData(int id) {
        return consumeMeta(meta -> meta.setCustomModelData(id));
    }

    public KItemBuilder getCustomModelData() {
        return consumeMeta(ItemMeta::getCustomModelData);
    }

    public KItemBuilder setGlow(boolean mode) {
        return consumeMeta(meta -> {
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        });
    }

    public ItemStack build() {
        return this.itemStack;
    }
    
}
