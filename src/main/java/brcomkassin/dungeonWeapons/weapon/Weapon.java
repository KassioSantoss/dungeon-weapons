package brcomkassin.dungeonWeapons.weapon;

import brcomkassin.dungeonWeapons.utils.KPDCUtil;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.context.AbilityContext;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
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
    protected WeaponType type;
    protected Set<AbilityType> abilities;
    protected ItemStack weaponItem;
    protected WeaponParticleMetadata particleMetadata;
    protected WeaponAbility currentAbility;
    protected List<AbilityType> availableAbilities;

    public Weapon(String name, UUID id, WeaponType type, Material material,
                  WeaponParticleMetadata particleMetadata, List<AbilityType> availableAbilities) {
        this(Component.text(name), id, type, material, particleMetadata, availableAbilities);
    }

    public Weapon(Component displayName, UUID id, WeaponType type, Material material,
                  WeaponParticleMetadata particleMetadata, List<AbilityType> availableAbilities) {
        this.displayName = displayName;
        this.id = id != null ? id : UUID.randomUUID();
        this.type = type;
        this.abilities = new LinkedHashSet<>();
        this.particleMetadata = particleMetadata;
        this.availableAbilities = new ArrayList<>(availableAbilities);

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

    public void addAvailableAbility(AbilityType ability) {
        this.availableAbilities.add(ability);
    }

    public void unlockAbilities(Player player, AbilityType ability) {
        if (!availableAbilities.contains(ability)) {
            KColoredLogger.error("[unlockAbilities]: Ability " + ability.getAbility().getName()
                    + " not found in available abilities");
            return;
        }
        addAbility(player, ability);
    }

    private void addAbility(Player player, AbilityType ability) {
        if (abilities.contains(ability)) return;
        this.abilities.add(ability);
        if (currentAbility == null) {
            this.currentAbility = ability.getAbility();
        }
        savePDC(player);
    }

    public void removeAbility(Player player, AbilityType ability) {
        if (!abilities.contains(ability)) return;
        this.abilities.remove(ability);
        if (currentAbility != null && currentAbility.equals(ability.getAbility())) {
            this.currentAbility = abilities.stream()
                    .findFirst()
                    .map(AbilityType::getAbility)
                    .orElse(null);
        }
        savePDC(player);
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

    @Override
    public String toString() {
        return "Weapon{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", abilities=" + abilities +
                '}';
    }

}
