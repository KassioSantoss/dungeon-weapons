package brcomkassin.dungeonWeapons.weapon.data;

import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.ability.AbilityType;
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
    private String type;
    private List<String> abilities;
    private String currentAbility;
    private List<String> availableAbilities;
    private WeaponParticleMetadata particleMetadata;
    private Material material;

    public WeaponData(Weapon weapon) {
        this.id = weapon.getId();
        this.material = weapon.getWeaponItem().getType();
        this.displayName = weapon.getDisplayName();
        this.type = weapon.getType().name();
        this.abilities = weapon.getAbilities().stream().map(AbilityType::name).toList();
        this.currentAbility = weapon.getCurrentAbility() != null ? weapon.getCurrentAbility().getType().name() : null;
        this.availableAbilities = weapon.getAvailableAbilities().stream().map(AbilityType::name).toList();
        this.particleMetadata = weapon.getParticleMetadata();
    }

    @Override
    public String toString() {
        return "WeaponData{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", type='" + type + '\'' +
                ", abilities=" + abilities +
                ", currentAbility='" + currentAbility + '\'' +
                ", availableAbilities=" + availableAbilities +
                '}';
    }
}