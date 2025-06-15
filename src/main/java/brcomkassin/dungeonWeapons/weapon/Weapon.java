package brcomkassin.dungeonWeapons.weapon;

import brcomkassin.dungeonWeapons.registry.AbilityRegistry;
import brcomkassin.dungeonWeapons.ability.AvailableAbilities;
import brcomkassin.dungeonWeapons.utils.KPDCUtil;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.utils.KColoredLogger;
import brcomkassin.dungeonWeapons.utils.KItemBuilder;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.weapon.data.WeaponSerializer;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
@Setter
public abstract class Weapon {

    protected Component displayName;
    protected UUID id;
    protected String genericType;
    protected Set<WeaponAbility> abilities;
    protected ItemStack weaponItem;
    protected WeaponParticleMetadata particleMetadata;
    protected WeaponAbility currentAbility;
    protected AvailableAbilities availableAbilities;

    public Weapon(String name,  String genericType, Material material,
                  WeaponParticleMetadata particleMetadata, AvailableAbilities availableAbilities) {
        this(Component.text(name), genericType, material, particleMetadata, availableAbilities);
    }

    public Weapon(Component displayName,  String genericType, Material material,
                  WeaponParticleMetadata particleMetadata, AvailableAbilities availableAbilities) {
        this.displayName = displayName;
        this.id = UUID.randomUUID();
        this.genericType = genericType;
        this.abilities = new LinkedHashSet<>();
        this.particleMetadata = particleMetadata;
        this.availableAbilities = availableAbilities;

        this.weaponItem = KItemBuilder.of(material)
                .setName(displayName)
                .build();
    }

    public void savePDC(Player player) {
        String serialize = WeaponSerializer.serialize(this);
        this.weaponItem = KPDCUtil.savePDC(weaponItem, WeaponIds.WEAPON_KEY, serialize);
        this.weaponItem = KPDCUtil.savePDC(weaponItem, WeaponIds.WEAPON_ID_KEY, id.toString());
        ItemStack build = KItemBuilder.of(this.weaponItem)
                .setName(displayName)
                .build();

        player.getInventory().setItemInMainHand(build);
    }

    public void addAvailableAbility(WeaponAbility ability) {
        this.availableAbilities.addAbility(ability);
    }

    public boolean unlockAbilities(Player player, WeaponAbility ability) {
        if (!availableAbilities.containsAbility(ability)) {
            KColoredLogger.error("[unlockAbilities]: Ability " + AbilityRegistry.getAbility(ability.getName()).getName()
                    + " not found in available abilities");
            return false;
        }
        addAbility(player, ability);
        return true;
    }

    private void addAbility(Player player, WeaponAbility weaponAbility) {
        if (abilities.contains(weaponAbility)) return;
        this.abilities.add(weaponAbility);
        if (currentAbility == null) {
            this.currentAbility = weaponAbility;
        }
        savePDC(player);
    }

    public boolean removeAbility(Player player, WeaponAbility ability) {
        if (!abilities.contains(ability)) return false;
        this.abilities.remove(ability);
        if (currentAbility != null && currentAbility.equals(ability)) {
            this.currentAbility = abilities.stream()
                    .findFirst()
                    .get();
        }
        savePDC(player);
        return true;
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

        List<WeaponAbility> ordered = new ArrayList<>(abilities);
        if (currentAbility == null || !abilities.contains(currentAbility)) {
            currentAbility = ordered.get(0);
        } else {
            int index = ordered.indexOf(currentAbility);
            int nextIndex = (index + 1) % ordered.size();
            currentAbility = ordered.get(nextIndex);
        }
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "id='" + id + '\'' +
                ", genericType=" + genericType +
                ", abilities=" + abilities +
                '}';
    }

}
