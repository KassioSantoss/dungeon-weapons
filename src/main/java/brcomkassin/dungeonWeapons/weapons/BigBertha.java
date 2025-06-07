package brcomkassin.dungeonWeapons.weapons;

import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.WeaponType;
import brcomkassin.dungeonWeapons.skills.RepulsionWaveAbility;
import brcomkassin.dungeonWeapons.skills.WeaponAbility;

public class BigBertha extends Weapon {

    public BigBertha() {
        super("&4Big &fBertha", "big_bertha", WeaponType.SWORD);
        addAbility(new RepulsionWaveAbility());
    }

}
