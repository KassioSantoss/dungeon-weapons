package brcomkassin.dungeonWeapons;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Particle;

@Getter
@Setter
public class WeaponParticleMetadata {

    private Color color;
    private Particle type;
    private int size;

    private WeaponParticleMetadata(Color color, Particle type, int size) {
        this.color = color;
        this.type = type;
        this.size = size;
    }

    public static class Builder {
        private Color color;
        private Particle type;
        private int size;
        private Builder instance;

        public static Builder of() {
            return new Builder();
        }

        public Builder color(Color color) {
            this.color = color;
            return this;
        }

        public Builder type(Particle type) {
            this.type = type;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public WeaponParticleMetadata build() {
            if (color == null && type == Particle.DUST) color = Color.WHITE;
            if (size == 0) size = 1;
            return new WeaponParticleMetadata(color, type, size);
        }

    }

}
