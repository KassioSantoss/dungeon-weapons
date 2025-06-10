package brcomkassin.dungeonWeapons.weapons;

import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.WeaponParticleMetadata;
import brcomkassin.dungeonWeapons.WeaponType;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.ability.JumpAbility;
import brcomkassin.dungeonWeapons.ability.RepulsionWaveAbility;
import brcomkassin.dungeonWeapons.utils.ItemBuilder;
import brcomkassin.dungeonWeapons.utils.MessageText;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.UUID;

public class BigBertha extends Weapon {

    public BigBertha() {
        super(MessageText.create()
                        .text("B").color(255, 34, 34)
                        .text("i").color(255, 96, 96)
                        .text("g ").color(255, 150, 150)
                        .text("Ber").color(255, 178, 178)
                        .text("tha").color(196, 105, 105)
                        .build(),
                UUID.randomUUID(), WeaponType.BIG_BERTHA, Material.NETHERITE_SWORD,
                WeaponParticleMetadata.Builder.of()
                        .type(Particle.DUST)
                        .color(Color.fromRGB(255, 122, 122))
                        .size(4)
                        .build(),
                List.of(AbilityType.JUMP, AbilityType.REPULSION_WAVE));
    }

}
