package brcomkassin.dungeonWeapons.weapon.item;

import brcomkassin.dungeonWeapons.ability.utils.AbilitiesName;
import brcomkassin.dungeonWeapons.ability.AvailableAbilities;
import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import org.bukkit.Material;

public class BigBertha extends Weapon {

    public BigBertha() {
        super(KGradient.apply("Big Bertha", KGradient.Reds.SCARLET.start(), KGradient.Reds.LAVA.end(), true), "Big_Bertha",
                Material.NETHERITE_SWORD,
                WeaponParticleMetadata.Builder.of()
                        .color(255, 122, 122)
                        .size(4)
                        .build(),
                new AvailableAbilities(AbilitiesName.REPULSION_WAVE, AbilitiesName.JUMP, AbilitiesName.METEOR_FALL));
    }

}
