package brcomkassin.dungeonWeapons.weapon.item;

import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponType;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import org.bukkit.Color;
import org.bukkit.Material;
import java.util.List;
import java.util.UUID;

public class RoyalSword extends Weapon {

    public RoyalSword() {
        super(KGradient.apply("Royal Sword", KGradient.MultiColor.HYPER, true),
                UUID.randomUUID(), WeaponType.ROYAL_SWORD, Material.NETHERITE_SWORD,
                WeaponParticleMetadata.Builder.of()
                        .color(Color.BLUE)
                        .size(5)
                        .build(),
                List.of(AbilityType.JUMP, AbilityType.REPULSION_WAVE));
    }

}
