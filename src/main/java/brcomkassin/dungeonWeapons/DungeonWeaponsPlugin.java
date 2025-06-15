package brcomkassin.dungeonWeapons;

import brcomkassin.dungeonWeapons.ability.ConnectPlayersAbility;
import brcomkassin.dungeonWeapons.ability.JumpAbility;
import brcomkassin.dungeonWeapons.ability.MeteorFallAbility;
import brcomkassin.dungeonWeapons.ability.RepulsionWaveAbility;
import brcomkassin.dungeonWeapons.initializer.WeaponInitializer;
import brcomkassin.dungeonWeapons.weapon.item.BigBertha;
import brcomkassin.dungeonWeapons.weapon.item.RoyalSword;
import lombok.Getter;

import org.bukkit.plugin.java.JavaPlugin;

public final class DungeonWeaponsPlugin extends JavaPlugin {
    @Getter
    private static DungeonWeaponsPlugin instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        WeaponInitializer weaponInitializer = WeaponInitializer.of();
        weaponInitializer.init();
        weaponInitializer.registryAbilities(JumpAbility::new);
        weaponInitializer.registryAbilities(ConnectPlayersAbility::new);
        weaponInitializer.registryAbilities(RepulsionWaveAbility::new);
        weaponInitializer.registryAbilities(MeteorFallAbility::new);

        weaponInitializer.registryWeapons(BigBertha::new);
        weaponInitializer.registryWeapons(RoyalSword::new);
    }

    @Override
    public void onDisable() {

    }

}
