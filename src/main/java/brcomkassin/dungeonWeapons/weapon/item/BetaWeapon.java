package brcomkassin.dungeonWeapons.weapon.item;

import brcomkassin.dungeonWeapons.ability.utils.AbilitiesName;
import brcomkassin.dungeonWeapons.ability.utils.AvailableAbilities;
import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponInfo;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import org.bukkit.Material;

public class BetaWeapon extends Weapon {

    public BetaWeapon() {
        super(
                WeaponInfo.builder()
                        .displayName(
                                KGradient.apply("Beta", KGradient.PurplesAndMagenta.ELDRITCH.start(), KGradient.BluesAndCyans.DEEP_SEA.end(), true))
                        .material(Material.NETHERITE_SWORD)
                        .generic("beta_weapon")
                        .availableAbilities(
                                AvailableAbilities.of(AbilitiesName.METEOR_FALL)
                        )
                        .particleMetadata(
                                WeaponParticleMetadata.Builder.of()
                                        .color(255, 122, 122)
                                        .size(4)
                                        .build())
                        .build()
        );
    }
}
