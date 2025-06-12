package brcomkassin.dungeonWeapons.ability;

import brcomkassin.dungeonWeapons.context.AbilityContext;

public interface WeaponAbility {
    void execute(AbilityContext abilityContext);

    String getName();

    AbilityType getType();

    default boolean requiresRightClick() {
        return false;
    }

    default boolean requiresTarget() {
        return false;
    }

}
