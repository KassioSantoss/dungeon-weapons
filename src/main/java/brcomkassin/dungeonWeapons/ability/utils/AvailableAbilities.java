package brcomkassin.dungeonWeapons.ability.utils;

import brcomkassin.dungeonWeapons.ability.WeaponAbility;

import java.util.ArrayList;
import java.util.List;

public class AvailableAbilities {

    private List<WeaponAbility> abilities = new ArrayList<>();

    public AvailableAbilities(WeaponAbility... weaponAbilities) {
        abilities = List.of(weaponAbilities);
    }

    public AvailableAbilities(List<WeaponAbility> weaponAbilities) {
        abilities = weaponAbilities;
    }

    public List<WeaponAbility> getAbilities() {
        return abilities;
    }

    public void addAbility(WeaponAbility ability) {
        abilities.add(ability);
    }

    public void removeAbility(WeaponAbility ability) {
        abilities.remove(ability);
    }

    public boolean containsAbility(WeaponAbility ability) {
        return abilities.contains(ability);
    }

}
