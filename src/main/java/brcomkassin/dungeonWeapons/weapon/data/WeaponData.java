package brcomkassin.dungeonWeapons.weapon.data;

import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.ability.utils.AvailableAbilities;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class WeaponData {
    private UUID id;
    private Component displayName;
    private String genericType;
    private List<String> abilities;
    private String currentAbility;
    private List<String> availableAbilities;
    private WeaponParticleMetadata particleMetadata;
    private Material material;
    private boolean isCustomModelItem;

    public WeaponData(Weapon weapon) {
        this.id = weapon.getId();
        this.material = weapon.getWeaponItem().getType();
        this.displayName = weapon.getDisplayName();
        this.genericType = weapon.getGenericType();
        this.abilities = weapon.getAbilities().stream().map(WeaponAbility::getName)
                .toList();
        this.currentAbility = weapon.getCurrentAbility() != null ? weapon.getCurrentAbility().getName() : null;
        this.availableAbilities = weapon.getAvailableAbilities().getAbilities().stream().map(WeaponAbility::getName).toList();
        this.particleMetadata = weapon.getParticleMetadata();
        this.isCustomModelItem = weapon.isCustomModelItem();
    }

    @Override
    public String toString() {
        return "WeaponData{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", genericType='" + genericType + '\'' +
                ", abilities=" + abilities +
                ", currentAbility='" + currentAbility + '\'' +
                ", availableAbilities=" + availableAbilities +
                '}';
    }

}