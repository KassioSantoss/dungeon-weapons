package brcomkassin.dungeonWeapons.weapon.data;

import brcomkassin.dungeonWeapons.ability.AvailableAbilities;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.registry.AbilityRegistry;
import brcomkassin.dungeonWeapons.weapon.Weapon;

import java.util.stream.Collectors;

public class WeaponInstance extends Weapon {

    public WeaponInstance(WeaponData data) {
        super(data.getDisplayName(), data.getId(), data.getGenericType(), data.getMaterial(),
                data.getParticleMetadata(), new AvailableAbilities(data.getAvailableAbilities().stream()
                        .map(AbilityRegistry::getAbility).toList()));

        this.abilities = data.getAbilities().stream().map(AbilityRegistry::getAbility).collect(Collectors.toSet());
        this.id = data.getId();
        if (data.getCurrentAbility() != null) {
            this.currentAbility = AbilityRegistry.getAbility(data.getCurrentAbility());
        }
    }

}
