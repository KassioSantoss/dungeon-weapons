package brcomkassin.dungeonWeapons.weapons;

import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.WeaponType;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.ability.RepulsionWaveAbility;
import brcomkassin.dungeonWeapons.utils.ItemBuilder;
import brcomkassin.dungeonWeapons.utils.MessageText;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.List;

public class RoyalSword extends Weapon {
    public RoyalSword() {
        super(MessageText.create()
                        .text("Roy").color(217, 174, 0)
                        .text("al").color(214, 178, 33)
                        .text(" Swo").color(245, 216, 98)
                        .text("rd").color(240, 198, 30)
                        .build(), "royal_sword", WeaponType.ROYAL_SWORD, Material.NETHERITE_SWORD,
                WeaponParticleMetadata.Builder.of()
                        .color(Color.BLUE)
                        .size(5)
                        .type(Particle.DUST)
                        .build(),
                List.of(AbilityType.JUMP, AbilityType.REPULSION_WAVE));
        unlockAbilities(AbilityType.REPULSION_WAVE);
    }

    @Override
    protected Weapon createClone() {
        return new RoyalSword();
    }
}
