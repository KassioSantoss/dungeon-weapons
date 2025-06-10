package brcomkassin.dungeonWeapons.ability;

import lombok.Getter;

@Getter
public enum AbilityType {
    JUMP(new JumpAbility()),
    REPULSION_WAVE(new RepulsionWaveAbility());

    private final WeaponAbility ability;

    AbilityType(WeaponAbility ability) {
        this.ability = ability;
    }

    public static AbilityType fromName(String name) {
        for (AbilityType type : values()) {
            if (type.name().equalsIgnoreCase(name)) return type;
        }
        return null;
    }

}
