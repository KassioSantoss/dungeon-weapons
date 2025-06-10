package brcomkassin.dungeonWeapons.data;

import brcomkassin.dungeonWeapons.WeaponParticleMetadata;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class WeaponData {
    public String id;
    public String displayName;
    public String type;
    public List<String> abilities;
    public String currentAbility;
    public List<String> availableAbilities;
    public WeaponParticleMetadata particleMetadata;
    public int version = 1;

    @Override
    public String toString() {
        return "WeaponData{" +
                "id='" + id + '\'' +
                ", displayName='" + displayName + '\'' +
                ", type='" + type + '\'' +
                ", abilities=" + abilities +
                ", currentAbility='" + currentAbility + '\'' +
                ", availableAbilities=" + availableAbilities +
                '}';
    }
}