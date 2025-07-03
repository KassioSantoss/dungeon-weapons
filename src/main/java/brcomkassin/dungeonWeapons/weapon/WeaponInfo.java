package brcomkassin.dungeonWeapons.weapon;

import brcomkassin.dungeonWeapons.ability.utils.AvailableAbilities;
import brcomkassin.dungeonWeapons.weapon.data.WeaponParticleMetadata;
import lombok.Builder;
import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

@Data
@Builder
public class WeaponInfo {

    private Component displayName;
    private String generic;
    private Material material;
    private WeaponParticleMetadata particleMetadata;
    private AvailableAbilities availableAbilities;
    private int customModelData;
    private boolean isCustomModelItem;

}
