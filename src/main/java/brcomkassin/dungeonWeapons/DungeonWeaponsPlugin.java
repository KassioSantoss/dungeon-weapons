package brcomkassin.dungeonWeapons;

import brcomkassin.dungeonWeapons.initializer.WeaponInitializer;
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
        WeaponInitializer.of().init();
    }

    @Override
    public void onDisable() {

    }
}
