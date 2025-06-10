package brcomkassin.dungeonWeapons.command;

import brcomkassin.dungeonWeapons.*;
import brcomkassin.dungeonWeapons.ability.AbilityType;
import brcomkassin.dungeonWeapons.manager.WeaponManager;
import brcomkassin.dungeonWeapons.utils.ColoredLogger;
import brcomkassin.dungeonWeapons.utils.Message;
import brcomkassin.dungeonWeapons.utils.MessageText;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        if (!(sender instanceof Player player)) return true;

        if (args.length < 1) {
            player.sendMessage(Component.text("Uso: /weapon <give|ability>").color(NamedTextColor.RED));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "give" -> handleGive(player, args);
            case "ability" -> handleAbility(player, args);
            case "pdc" -> Message.Chat.send(player, "" +
                    player.getInventory().getItemInMainHand().getPersistentDataContainer().getKeys());
            default ->
                    player.sendMessage(Component.text("Subcomando desconhecido. Use give ou ability.").color(NamedTextColor.RED));
        }

        return true;
    }

    private void handleGive(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(Component.text("Uso: /weapon give <id>").color(NamedTextColor.RED));
            return;
        }

        Weapon weapon = WeaponManager.of().getWeapon(args[1].toUpperCase());

        if (weapon == null) {
            player.sendMessage(Component.text("Arma inválida.").color(NamedTextColor.RED));
            return;
        }

        weapon.savePDC(player);

        Component component = MessageText.create().text("Você recebeu o ").color(0, 160, 217).bold()
                .text(weapon.getDisplayName())
                .text(" com sucesso!").color(0, 160, 217).bold()
                .build();

        player.sendMessage(component);
    }

    private void handleAbility(Player player, String[] args) {
        if (args.length < 3) {
            player.sendMessage(Component.text("Uso: /weapon ability <add|remove> <ability>").color(NamedTextColor.RED));
            return;
        }

        String action = args[1].toLowerCase();
        String abilityName = args[2].toUpperCase();

        AbilityType abilityType = AbilityType.fromName(abilityName);
        if (abilityType == null) {
            player.sendMessage(Component.text("Habilidade inválida: " + abilityName).color(NamedTextColor.RED));
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir()) {
            player.sendMessage(Component.text("Você precisa estar segurando uma arma.").color(NamedTextColor.RED));
            return;
        }

        String readPDC = PDCUtil.readPDC(item, WeaponIds.WEAPON_KEY);
        WeaponData deserialize = WeaponSerializer.deserialize(readPDC);

        ColoredLogger.info("deserialize COMMAND: " + deserialize);
        Weapon weapon = WeaponManager.of().getWeaponByUUID(deserialize.getId());

        if (weapon == null) {
            player.sendMessage(Component.text("Este item não é uma arma válida.").color(NamedTextColor.RED));
            return;
        }

        switch (action) {
            case "add" -> {
                weapon.unlockAbilities(player, abilityType);
                Component component = MessageText.create().text("Habilidade ").color(0, 160, 217)
                        .text(abilityType.name()).color(0, 160, 217)
                        .text(" adicionada à ").color(0, 160, 217)
                        .text(weapon.getDisplayName())
                        .text(" com sucesso!").color(0, 160, 217)
                        .build();
                player.sendMessage(component);
            }

            case "remove" -> {
                weapon.removeAbility(player, abilityType);
                Component component = MessageText.create().text("Habilidade ").color(0, 160, 217)
                        .text(abilityType.name()).color(0, 160, 217)
                        .text(" removida da ").color(0, 160, 217)
                        .text(weapon.getDisplayName())
                        .text(" com sucesso!").color(0, 160, 217)
                        .build();
                player.sendMessage(component);
            }

            default ->
                    player.sendMessage(Component.text("Ação inválida. Use add ou remove.").color(NamedTextColor.RED));
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
