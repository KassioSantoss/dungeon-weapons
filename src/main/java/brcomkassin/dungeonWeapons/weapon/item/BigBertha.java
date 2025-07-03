package brcomkassin.dungeonWeapons.weapon.item;

import brcomkassin.dungeonWeapons.ability.utils.AbilitiesName;
import brcomkassin.dungeonWeapons.ability.utils.AvailableAbilities;
import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponInfo;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import org.bukkit.Material;

public class BigBertha extends Weapon {

    public BigBertha() {
        super(WeaponInfo.builder()
                .displayName(
                        KGradient.apply("Big Bertha", KGradient.Reds.SCARLET.start(), KGradient.Reds.LAVA.end(), true))
                .material(Material.NETHERITE_SWORD)
                .generic("big_bertha")
                .availableAbilities(
                        AvailableAbilities.of(AbilitiesName.METEOR_FALL, AbilitiesName.JUMP,
                                AbilitiesName.REPULSION_WAVE,AbilitiesName.CONNECT_PLAYERS)
                )
                .particleMetadata(
                        WeaponParticleMetadata.Builder.of()
                                .color(255, 122, 122)
                                .size(4)
                                .build())
                .customModelData(10)
                .isCustomModelItem(true)
                .build());
    }

}
