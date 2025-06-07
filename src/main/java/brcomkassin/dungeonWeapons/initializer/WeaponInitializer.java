package brcomkassin.dungeonWeapons.initializer;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.command.WeaponCommand;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.listeners.WeaponListener;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import brcomkassin.dungeonWeapons.weapons.BigBertha;

import java.util.List;

public class WeaponInitializer {

    private final static WeaponInitializer INSTANCE = new WeaponInitializer();
    private final DungeonWeaponsPlugin plugin = DungeonWeaponsPlugin.getInstance();
    private final WeaponManager weaponManager;

    private WeaponInitializer() {
        this.weaponManager = new WeaponManager();
    }

    public static WeaponInitializer of() {
        return INSTANCE;
    }

    public void init() {
        registerAllWeapons(List.of(
                new BigBertha()
        ));
        enable();
    }

    private void registerAllWeapons(List<Weapon> weapons) {
        ColoredLogger.info("&aRegistering weapons...");
        weapons.forEach(WeaponRegistry::register);
    }

    private void enable() {
        plugin.getServer().getPluginManager().registerEvents(new WeaponListener(weaponManager), plugin);
        plugin.getCommand("weapon").setExecutor(new WeaponCommand(weaponManager));
    }
}
