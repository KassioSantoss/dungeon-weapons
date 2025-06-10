package brcomkassin.dungeonWeapons.weapon.item;

import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.WeaponType;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.utils.MessageText;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.List;
import java.util.UUID;

public class RoyalSword extends Weapon {
    public RoyalSword() {
        super(MessageText.create()
                        .text("Roy").color(217, 174, 0)
                        .text("al").color(214, 178, 33)
                        .text(" Swo").color(245, 216, 98)
                        .text("rd").color(240, 198, 30)
                        .build(),
                UUID.randomUUID(), WeaponType.ROYAL_SWORD, Material.NETHERITE_SWORD,
                WeaponParticleMetadata.Builder.of()
                        .color(Color.BLUE)
                        .size(5)
                        .type(Particle.DUST)
                        .build(),
                List.of(AbilityType.JUMP, AbilityType.REPULSION_WAVE));
    }

}
