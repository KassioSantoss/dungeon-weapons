package brcomkassin.dungeonWeapons.command;


import brcomkassin.dungeonWeapons.DungeonMessages;
import brcomkassin.dungeonWeapons.DungeonWeaponsPlugin;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.weapon.WeaponType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WeaponCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(DungeonMessages.error("Uso: /weapon <give|ability>"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "give" -> handleGive(player, args);
            case "ability" -> handleAbility(player, args);
            case "test" -> BlockGlowService.getInstance(DungeonWeaponsPlugin.getInstance()).spawnBlock(player);
            default -> player.sendMessage(DungeonMessages.error("Subcomando desconhecido. Use <give|ability>."));
        }

        return true;
    }

    private void handleGive(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(DungeonMessages.error("Uso: /weapon give <id_da_arma>"));
            return;
        }

        Weapon weapon = WeaponManager.of().getWeapon(args[1].toUpperCase());

        if (weapon == null) {
            player.sendMessage(DungeonMessages.error("Arma inválida."));
            return;
        }

        weapon.savePDC(player);

        player.sendMessage(DungeonMessages.giveWeaponSuccess(weapon));
    }

    private void handleAbility(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(DungeonMessages.error("Uso: /weapon ability <add|remove> <habilidade>"));
            return;
        }

        String action = args[1].toLowerCase();
        String abilityName = args[2].toUpperCase();

        AbilityType abilityType;
        try {
            abilityType = AbilityType.valueOf(abilityName);
        } catch (IllegalArgumentException e) {
            player.sendMessage(DungeonMessages.error("Habilidade inválida: " + abilityName));
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            player.sendMessage(DungeonMessages.error("Você precisa estar segurando uma arma."));
            return;
        }

        Weapon weapon = WeaponManager.of().getWeapon(player.getInventory().getItemInMainHand());

        if (weapon == null) {
            player.sendMessage(DungeonMessages.error("Este item não é uma arma válida."));
            return;
        }

        switch (action) {
            case "add" -> {
                weapon.unlockAbilities(player, abilityType);
                player.sendMessage(DungeonMessages.abilitySuccess("adicionada", abilityType.name(), weapon));
            }
            case "remove" -> {
                weapon.removeAbility(player, abilityType);
                player.sendMessage(DungeonMessages.abilitySuccess("removida", abilityType.name(), weapon));
            }
            default -> player.sendMessage(DungeonMessages.error("Ação inválida. Use <add|remove>."));
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("give", "ability");
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "give" -> {
                    return Arrays.stream(WeaponType.values()).map(Enum::name)
                            .map(String::toLowerCase)
                            .collect(Collectors.toList());
                }
                case "ability" -> {
                    return List.of("add", "remove");
                }
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("ability")) {
            return Arrays.stream(AbilityType.values())
                    .map(Enum::name)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        }

        return List.of();
    }
}
