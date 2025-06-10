package brcomkassin.dungeonWeapons.initializer;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.command.WeaponCommand;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.listeners.WeaponListener;

public class WeaponInitializer {

    private final static WeaponInitializer INSTANCE = new WeaponInitializer();
    private final DungeonWeaponsPlugin plugin = DungeonWeaponsPlugin.getInstance();

    private final WeaponManager weaponManager;

    private WeaponInitializer() {
        weaponManager = WeaponManager.of();
    }

    public static WeaponInitializer of() {
        return INSTANCE;
    }

    public void init() {
        enable();
    }

    private void enable() {
        plugin.getServer().getPluginManager().registerEvents(new WeaponListener(weaponManager), plugin);
        plugin.getCommand("weapon").setExecutor(new WeaponCommand());
    }
}
