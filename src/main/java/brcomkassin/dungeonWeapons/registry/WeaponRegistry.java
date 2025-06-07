package brcomkassin.dungeonWeapons.registry;

import brcomkassin.dungeonWeapons.Weapon;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class WeaponRegistry {

    @Getter
    private static final List<Weapon> weapons = new ArrayList<>();

    public static void register(Weapon weapon) {
        weapons.add(weapon);
    }

    public static Weapon getWeapon(String id) {
        return weapons.stream().filter(weapon -> weapon.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

}
