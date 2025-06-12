package brcomkassin.dungeonWeapons.weapon.data;

import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponType;

import java.util.stream.Collectors;

public class WeaponInstance extends Weapon {

    public WeaponInstance(WeaponData data) {
        super(data.getDisplayName(), data.getId(), WeaponType.fromType(data.getType()), data.getMaterial(),
                data.getParticleMetadata(), data.getAvailableAbilities().stream().map(AbilityType::valueOf).toList());

        this.abilities = data.getAbilities().stream().map(AbilityType::valueOf).collect(Collectors.toSet());
        this.id = data.getId();
        if (data.getCurrentAbility() != null) {
            this.currentAbility = AbilityType.valueOf(data.getCurrentAbility().toUpperCase()).getAbility();
        }
    }

}
