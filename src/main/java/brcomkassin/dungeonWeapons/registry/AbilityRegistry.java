package brcomkassin.dungeonWeapons.registry;

import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.utils.KColoredLogger;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AbilityRegistry {

    @Getter
    private static final Map<String, WeaponAbility> abilities = new HashMap<>();

    public static void registerAbility(WeaponAbility ability) {
        abilities.put(ability.getName(), ability);
    }

    public static void unregisterAbility(WeaponAbility ability) {
        abilities.remove(ability.getName());
    }

    public static WeaponAbility getAbility(String abilityName) {
        return abilities.get(abilityName);
    }

}
