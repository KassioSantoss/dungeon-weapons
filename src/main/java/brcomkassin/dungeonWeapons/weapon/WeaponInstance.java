package brcomkassin.dungeonWeapons.weapon;

import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.weapon.data.WeaponData;

import java.util.stream.Collectors;

public class WeaponInstance extends Weapon {

    public WeaponInstance(WeaponData data) {
        super(data.displayName, data.id, WeaponType.fromType(data.type), data.material,
                data.particleMetadata, data.availableAbilities.stream().map(AbilityType::valueOf).toList());

        this.abilities = data.abilities.stream().map(AbilityType::valueOf).collect(Collectors.toSet());
        this.id = data.getId();
        if (data.currentAbility != null) {
            this.currentAbility = AbilityType.valueOf(data.currentAbility.toUpperCase()).getAbility();
        }
    }

}
