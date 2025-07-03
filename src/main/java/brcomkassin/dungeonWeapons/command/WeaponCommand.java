package brcomkassin.dungeonWeapons.command;

import brcomkassin.dungeonWeapons.DungeonMessages;
import brcomkassin.dungeonWeapons.registry.AbilityRegistry;
import brcomkassin.dungeonWeapons.ability.WeaponAbility;
import brcomkassin.dungeonWeapons.registry.WeaponRegistry;
import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.utils.KItemBuilder;
import brcomkassin.dungeonWeapons.utils.KMessage;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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
            case "test" -> {
                ItemStack build = KItemBuilder.of(Material.SWEET_BERRIES)
                        .setName("&6Banana")
                        .setCustomModelData(1)
                        .build();
                player.getInventory().addItem(build);
            }
            case "ability" -> handleAbility(player, args);
            case "spawn" -> {
                if (!player.isOp()) return false;
                final int numberOfEntities = 30;
                final double radius = 5.0;
                final Location centerLocation = player.getLocation();

                for (int i = 0; i < numberOfEntities; i++) {
                    final double angle = 2 * Math.PI * i / numberOfEntities;

                    final double x = centerLocation.getX() + radius * Math.cos(angle);
                    final double z = centerLocation.getZ() + radius * Math.sin(angle);

                    final double y = centerLocation.getY();

                    final Location spawnLocation = new Location(player.getWorld(), x, y, z);

                    player.getWorld().spawnEntity(spawnLocation.add(0, 1, 0), EntityType.CHICKEN);
                }
            }
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
        String abilityName = args[2];

        WeaponAbility ability;
        try {
            ability = AbilityRegistry.getAbility(abilityName);
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
                boolean unlockAbilities = weapon.unlockAbilities(player, ability);

                if (unlockAbilities) {
                    player.sendMessage(DungeonMessages.abilitySuccess("desbloqueada", ability.getName(), weapon));
                } else {
                    player.sendMessage(DungeonMessages.error("Sua espada não tem essa habilidade."));
                }
            }
            case "remove" -> {
                boolean removeAbility = weapon.removeAbility(player, ability);
                if (removeAbility) {
                    player.sendMessage(DungeonMessages.abilitySuccess("removida", ability.getName(), weapon));
                } else {
                    player.sendMessage(DungeonMessages.error("Sua espada nao tem essa habilidade."));
                }
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
                    return WeaponRegistry.getRegisteredTypes().stream().toList();
                }
                case "ability" -> {
                    return List.of("add", "remove");
                }
            }
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("ability")) {
            return AbilityRegistry.getAbilities().values().stream().map(WeaponAbility::getName).toList();
        }

        return List.of();
    }

}
