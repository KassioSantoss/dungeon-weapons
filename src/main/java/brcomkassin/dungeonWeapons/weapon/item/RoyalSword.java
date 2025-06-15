package brcomkassin.dungeonWeapons.weapon.item;

import brcomkassin.dungeonWeapons.ability.utils.AbilitiesName;
import brcomkassin.dungeonWeapons.ability.AvailableAbilities;
import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import org.bukkit.Color;
import org.bukkit.Material;

public class RoyalSword extends Weapon {

    public RoyalSword() {
        super(KGradient.apply("Royal Sword", KGradient.MultiColor.HYPER, true), "Royal_Sword",
                Material.NETHERITE_SWORD,
                WeaponParticleMetadata.Builder.of()
                        .color(Color.BLUE)
                        .size(5)
                        .build(),
                new AvailableAbilities(AbilitiesName.CONNECT_PLAYERS));
    }

}
