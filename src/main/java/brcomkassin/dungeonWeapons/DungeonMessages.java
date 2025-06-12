package brcomkassin.dungeonWeapons;

import brcomkassin.dungeonWeapons.utils.KGradient;
import brcomkassin.dungeonWeapons.utils.KMessageText;
import brcomkassin.dungeonWeapons.weapon.Weapon;
import net.kyori.adventure.text.Component;

public final class DungeonMessages {

    private static final KGradient.Palette SUCCESS_PALETTE = KGradient.Greens.NEON;
    private static final KGradient.Palette ERROR_PALETTE = KGradient.Reds.SCARLET;
    private static final KGradient.Palette INFO_PALETTE = KGradient.BluesAndCyans.OCEAN;

    private static final KGradient.Palette ABILITY_PALETTE = KGradient.YellowsAndGolds.GOLD;

    private DungeonMessages() {
    }

    public static Component error(String message) {
        return KMessageText.create()
                .text("✖ ").color(ERROR_PALETTE.start()).bold()
                .text(message).color(ERROR_PALETTE.end())
                .build();
    }

    public static Component giveWeaponSuccess(Weapon weapon) {
        return KMessageText.create()
                .text("✔ ").color(SUCCESS_PALETTE.start()).bold()
                .text("Você recebeu ").color(SUCCESS_PALETTE.end())
                .text(weapon.getDisplayName())
                .text(" com sucesso!").color(SUCCESS_PALETTE.end())
                .build();
    }

    public static Component abilitySuccess(String action, String abilityName, Weapon weapon) {
        return KMessageText.create()
                .text("✔ ").color(SUCCESS_PALETTE.start()).bold()
                .text("Habilidade ").color(INFO_PALETTE.end())
                .text(KGradient.apply(abilityName, ABILITY_PALETTE, true))
                .text(" " + action + " à ").color(INFO_PALETTE.end())
                .text(weapon.getDisplayName())
                .text(" com sucesso!").color(INFO_PALETTE.end())
                .build();
    }
}
