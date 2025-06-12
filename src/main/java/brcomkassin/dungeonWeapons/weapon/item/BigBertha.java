package brcomkassin.dungeonWeapons.weapon.item;

import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponType;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import org.bukkit.Material;

import java.util.List;
import java.util.UUID;

public class BigBertha extends Weapon {

    public BigBertha() {
        super(KGradient.apply("Big Bertha", KGradient.Reds.SCARLET.start(), KGradient.Reds.LAVA.end(), true),
                UUID.randomUUID(), WeaponType.BIG_BERTHA, Material.NETHERITE_SWORD,
                WeaponParticleMetadata.Builder.of()
                        .color(255, 122, 122)
                        .size(4)
                        .build(),
                List.of(AbilityType.JUMP, AbilityType.REPULSION_WAVE,AbilityType.CONNECT_PLAYERS,AbilityType.METEOR_FALL));
    }

}
