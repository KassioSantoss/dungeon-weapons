package brcomkassin.dungeonWeapons;

import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import net.kyori.adventure.text.Component;

import java.util.stream.Collectors;

public class WeaponInstance extends Weapon {

    public WeaponInstance(WeaponData data) {
        super(data.displayName, data.id, WeaponType.fromType(data.type), data.material,
                data.particleMetadata, data.availableAbilities.stream().map(AbilityType::valueOf).toList());

        this.abilities = data.abilities.stream().map(AbilityType::valueOf).collect(Collectors.toSet());

        if (data.currentAbility != null) {
            this.currentAbility = AbilityType.valueOf(data.currentAbility.toUpperCase()).getAbility();
        }
    }

}
