package brcomkassin.dungeonWeapons;

import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.cache.WeaponCache;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.data.WeaponData;
import brcomkassin.dungeonWeapons.data.WeaponDataSerializer;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import brcomkassin.dungeonWeapons.utils.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class Weapon implements Cloneable {

    protected Component displayName;
    protected String id;
    protected WeaponType type;
    protected Set<AbilityType> abilities;
    protected ItemStack weaponItem;
    protected WeaponParticleMetadata particleMetadata;
    protected WeaponAbility currentAbility;
    protected List<AbilityType> availableAbilities;
    protected WeaponData weaponData;
    private WeaponManager weaponManager = WeaponManager.of();

    public Weapon(String name, String id, WeaponType type, Material material,
                  WeaponParticleMetadata particleMetadata, List<AbilityType> availableAbilities) {
        this(Component.text(name), id, type, material, particleMetadata, availableAbilities);
    }

    public Weapon(Component displayName, String id, WeaponType type, Material material,
                  WeaponParticleMetadata particleMetadata, List<AbilityType> availableAbilities) {
        this.displayName = displayName;
        this.id = id;
        this.type = type;
        this.abilities = new LinkedHashSet<>();
        this.particleMetadata = particleMetadata;
        this.availableAbilities = new ArrayList<>(availableAbilities);

        this.weaponItem = ItemBuilder.of(material)
                .setName(displayName)
                .build();
        initializeData();
    }

    public void saveWeaponStateToItem(Player player) {
        this.weaponData.id = this.id;
        this.weaponData.displayName = GsonComponentSerializer.gson().serialize(this.displayName);
        this.weaponData.type = this.type.name();
        this.weaponData.abilities = this.abilities.stream().map(AbilityType::name).collect(Collectors.toList());
        this.weaponData.currentAbility = this.currentAbility != null ? this.currentAbility.getType().name() : null;
        this.weaponData.availableAbilities = this.availableAbilities.stream().map(AbilityType::name).collect(Collectors.toList());
        this.weaponData.particleMetadata = this.particleMetadata;
        ItemStack itemStack = WeaponManager.of().saveDataToItem(this.weaponItem, weaponData);
        this.weaponItem = itemStack;
        ColoredLogger.info("[saveWeaponStateToItem]: =====================================");
        ColoredLogger.info("[saveWeaponStateToItem]: " + itemStack.getPersistentDataContainer());
        ColoredLogger.info("[saveWeaponStateToItem]: " + weaponData);
        ColoredLogger.info("[saveWeaponStateToItem]: =====================================");
        WeaponCache.addWeaponToCache(this);
    }

    private void initializeData() {
        this.weaponData = new WeaponData();
        this.weaponData.id = this.id;
        this.weaponData.displayName = GsonComponentSerializer.gson().serialize(this.displayName);
        this.weaponData.type = this.type.name();
        this.weaponData.abilities = this.abilities.stream().map(AbilityType::name).collect(Collectors.toList());
        this.weaponData.currentAbility = this.currentAbility != null ? this.currentAbility.getType().name() : null;
        this.weaponData.availableAbilities = this.availableAbilities.stream().map(AbilityType::name).collect(Collectors.toList());
        this.weaponData.particleMetadata = this.particleMetadata;
    }

    public void initWeaponState(WeaponData data) {
        this.id = data.id;
        this.displayName = GsonComponentSerializer.gson().deserialize(data.displayName);
        this.type = WeaponType.valueOf(data.type);
        this.abilities = data.abilities.stream()
                .map(AbilityType::valueOf)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        this.availableAbilities = data.availableAbilities.stream()
                .map(AbilityType::valueOf)
                .collect(Collectors.toList());
        this.currentAbility = data.currentAbility != null
                ? AbilityType.valueOf(data.currentAbility).getAbility()
                : null;
        this.particleMetadata = data.particleMetadata;
        this.weaponItem = WeaponManager.of().saveDataToItem(this.weaponItem, data);

        ColoredLogger.info("[initWeaponState]: ===================================");
        ColoredLogger.info("[initWeaponState]: " + weaponItem.getPersistentDataContainer());
        ColoredLogger.info("[initWeaponState]: ===================================");
    }

    public void addAvailableAbility(AbilityType ability) {
        this.availableAbilities.add(ability);
    }

    public void selectAbility(AbilityType ability) {
        if (abilities.contains(ability)) {
            this.currentAbility = ability.getAbility();
        }
    }

    public void unlockAbilities(AbilityType ability) {
        if (!availableAbilities.contains(ability)) {
            ColoredLogger.error("[unlockAbilities]: Ability " + ability.getAbility().getName()
                    + " not found in available abilities");
            return;
        }
        addAbility(ability);
    }

    private void addAbility(AbilityType ability) {
        if (abilities.contains(ability)) return;
        this.abilities.add(ability);
        if (currentAbility == null) {
            this.currentAbility = ability.getAbility();
        }
      //  saveWeaponStateToItem();
    }

    public void removeAbility(AbilityType ability) {
        if (!abilities.contains(ability)) return;
        this.abilities.remove(ability);
        if (currentAbility != null && currentAbility.equals(ability.getAbility())) {
            this.currentAbility = abilities.stream()
                    .findFirst()
                    .map(AbilityType::getAbility)
                    .orElse(null);
        }
    //    saveWeaponStateToItem();
    }

    public void removeAllAbilities() {
        this.abilities.clear();
        this.currentAbility = null;
    }

    public boolean hasAbility() {
        return !abilities.isEmpty();
    }

    public void performSkill(WeaponAbility weaponAbility, AbilityContext context) {
        if (weaponAbility == null || context == null) return;
        if (!hasAbility()) return;
        weaponAbility.execute(context);
    }

    public void cycleAbility() {
        if (!hasAbility()) return;

        List<AbilityType> ordered = new ArrayList<>(abilities);
        if (currentAbility == null || !abilities.contains(currentAbility.getType())) {
            currentAbility = ordered.get(0).getAbility();
        } else {
            int index = ordered.indexOf(currentAbility.getType());
            int nextIndex = (index + 1) % ordered.size();
            currentAbility = ordered.get(nextIndex).getAbility();
        }
    }

    public void removeNamespacedData(NamespacedKey key) {
        ItemMeta meta = weaponItem.getItemMeta();
        if (meta == null) return;

        meta.getPersistentDataContainer().remove(key);
        weaponItem.setItemMeta(meta);
    }

    public Weapon cloneWeapon() {
        Weapon clone = createClone();
        return clone;
    }

    protected abstract Weapon createClone();

    @Override
    public String toString() {
        return "Weapon{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", abilities=" + abilities +
                '}';
    }

}
