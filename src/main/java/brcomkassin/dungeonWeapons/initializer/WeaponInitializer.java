package brcomkassin.dungeonWeapons.initializer;

import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.registry.AbilityRegistry;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.command.WeaponCommand;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.listeners.WeaponListener;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import java.util.function.Supplier;

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

    public void registryWeapons(Supplier<Weapon> supplier) {
        String genericType = supplier.get().getGenericType();
        WeaponRegistry.register(genericType, supplier);
    }

    public void registryAbilities(Supplier<WeaponAbility> supplier) {
        WeaponAbility ability = supplier.get();
        AbilityRegistry.registerAbility(ability);
    }

}
