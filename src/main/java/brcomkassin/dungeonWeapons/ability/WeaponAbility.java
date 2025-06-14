package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.context.AbilityContext;

public interface WeaponAbility {
    void execute(AbilityContext abilityContext);

    String getName();

   // AbilityRegistry getType();

    default boolean requiresRightClick() {
        return false;
    }

    default boolean requiresTarget() {
        return false;
    }

}
