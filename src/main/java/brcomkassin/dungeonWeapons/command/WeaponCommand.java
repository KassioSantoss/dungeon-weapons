package brcomkassin.dungeonWeapons.command;

import brcomkassin.dungeonWeapons.Weapon;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WeaponCommand implements CommandExecutor, TabExecutor {

    private final WeaponManager weaponManager;

    public WeaponCommand(WeaponManager weaponManager) {
        this.weaponManager = weaponManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;

        if (strings.length < 1) return true;

        Weapon weapon = weaponManager.createWeaponByID(strings[0]);
        player.getInventory().addItem(weapon.getWeaponItem());

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1) return WeaponRegistry.getWeapons().stream().map(Weapon::getId).toList();
        return List.of();
    }

}
