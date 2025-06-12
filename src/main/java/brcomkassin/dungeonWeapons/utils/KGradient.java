package brcomkassin.dungeonWeapons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public final class KGradient {

    private KGradient() {
    }

    public record Palette(@NotNull TextColor start, @NotNull TextColor end) {
    }

    public static final class Reds {
        private Reds() {
        }

        public static final Palette FIRE = new Palette(TextColor.color(255, 80, 30), TextColor.color(255, 180, 50));
        public static final Palette LAVA = new Palette(TextColor.color(210, 40, 0), TextColor.color(255, 120, 20));
        public static final Palette MAGMA = new Palette(TextColor.color(180, 20, 0), TextColor.color(255, 90, 40));
        public static final Palette EMBER = new Palette(TextColor.color(255, 60, 0), TextColor.color(255, 140, 40));
        public static final Palette SCARLET = new Palette(TextColor.color(220, 20, 60), TextColor.color(255, 100, 100));
        public static final Palette CRIMSON = new Palette(TextColor.color(120, 0, 20), TextColor.color(220, 20, 60));
    }

    public static final class Pinks {
        private Pinks() {
        }

        public static final Palette BLOOM = new Palette(TextColor.color(240, 180, 200), TextColor.color(255, 220, 240));
        public static final Palette AETHER = new Palette(TextColor.color(180, 70, 255), TextColor.color(255, 150, 255)); // Tende mais para o rosa
    }

    public static final class OrangesAndBrowns {
        private OrangesAndBrowns() {
        }

        public static final Palette SUNSET = new Palette(TextColor.color(255, 120, 70), TextColor.color(255, 200, 120));
        public static final Palette AUTUMN = new Palette(TextColor.color(180, 80, 20), TextColor.color(240, 150, 40));
        public static final Palette PEACH = new Palette(TextColor.color(255, 229, 180), TextColor.color(255, 200, 160));
        public static final Palette COPPER = new Palette(TextColor.color(184, 115, 51), TextColor.color(210, 150, 100));
        public static final Palette BRONZE = new Palette(TextColor.color(205, 127, 50), TextColor.color(220, 150, 80));
        public static final Palette EARTH = new Palette(TextColor.color(100, 80, 50), TextColor.color(160, 130, 90));
    }

    public static final class YellowsAndGolds {
        private YellowsAndGolds() {
        }

        public static final Palette GOLD = new Palette(TextColor.color(247, 215, 29), TextColor.color(222, 189, 0));
        public static final Palette CREAM = new Palette(TextColor.color(255, 240, 200), TextColor.color(255, 220, 180));
    }

    public static final class Greens {
        private Greens() {
        }

        public static final Palette FOREST = new Palette(TextColor.color(30, 100, 40), TextColor.color(90, 180, 80));
        public static final Palette JUNGLE = new Palette(TextColor.color(20, 150, 100), TextColor.color(80, 220, 160));
        public static final Palette SWAMP = new Palette(TextColor.color(30, 60, 30), TextColor.color(90, 120, 60));
        public static final Palette MINT = new Palette(TextColor.color(80, 255, 180), TextColor.color(150, 255, 220));
        public static final Palette NEON = new Palette(TextColor.color(57, 255, 20), TextColor.color(150, 255, 150));
        public static final Palette DIGITAL = new Palette(TextColor.color(50, 255, 100), TextColor.color(150, 255, 200));
    }

    public static final class BluesAndCyans {
        private BluesAndCyans() {
        }

        public static final Palette OCEAN = new Palette(TextColor.color(20, 90, 200), TextColor.color(100, 180, 255));
        public static final Palette DEEP_SEA = new Palette(TextColor.color(0, 40, 80), TextColor.color(0, 100, 160));
        public static final Palette ICE = new Palette(TextColor.color(180, 220, 255), TextColor.color(230, 245, 255));
        public static final Palette GLACIER = new Palette(TextColor.color(160, 220, 255), TextColor.color(200, 255, 255));
        public static final Palette ROYAL = new Palette(TextColor.color(40, 60, 200), TextColor.color(100, 80, 230));
        public static final Palette SKY = new Palette(TextColor.color(135, 206, 235), TextColor.color(170, 220, 240));
        public static final Palette CRYSTAL = new Palette(TextColor.color(200, 240, 255), TextColor.color(150, 200, 255));
    }

    public static final class PurplesAndMagenta {
        private PurplesAndMagenta() {
        }

        public static final Palette TWILIGHT = new Palette(TextColor.color(60, 40, 180), TextColor.color(120, 90, 220));
        public static final Palette VOID = new Palette(TextColor.color(10, 0, 20), TextColor.color(60, 20, 100));
        public static final Palette ELDRITCH = new Palette(TextColor.color(100, 0, 100), TextColor.color(30, 0, 50));
        public static final Palette LILAC = new Palette(TextColor.color(200, 180, 255), TextColor.color(240, 210, 255));
        public static final Palette OBSIDIAN = new Palette(TextColor.color(20, 10, 30), TextColor.color(60, 30, 90));
    }

    public static final class MultiColor {
        private MultiColor() {
        }

        public static final Palette AURORA = new Palette(TextColor.color(50, 200, 120), TextColor.color(120, 90, 220));
        public static final Palette STELLAR = new Palette(TextColor.color(60, 120, 255), TextColor.color(255, 200, 255));
        public static final Palette HYPER = new Palette(TextColor.color(0, 220, 255), TextColor.color(255, 80, 220));
        public static final Palette CYBER = new Palette(TextColor.color(0, 255, 255), TextColor.color(255, 0, 255));
        public static final Palette CANDY = new Palette(TextColor.color(255, 200, 220), TextColor.color(200, 220, 255));
    }

    public static final class MonochromeAndMetallic {
        private MonochromeAndMetallic() {
        }

        public static final Palette GRAYSCALE = new Palette(TextColor.color(80, 80, 80), TextColor.color(180, 180, 180));
        public static final Palette ASH = new Palette(TextColor.color(120, 120, 120), TextColor.color(200, 200, 200));
        public static final Palette SILVER = new Palette(TextColor.color(192, 192, 192), TextColor.color(220, 220, 220));
        public static final Palette STEEL = new Palette(TextColor.color(100, 100, 110), TextColor.color(160, 160, 170));
    }

    public static Component apply(@NotNull Component component, @NotNull Palette palette, boolean bold) {
        String text = componentToPlainText(component);
        return apply(text, palette, bold);
    }

    public static Component apply(@NotNull String text, @NotNull TextColor start, @NotNull TextColor end, boolean bold) {
        Palette palette = new Palette(start, end);
        return apply(text, palette, bold);
    }

    public static Component apply(@NotNull String text, @NotNull Palette palette, boolean bold) {
        if (text.isEmpty()) return Component.empty();
        KMessageText messageText = KMessageText.create();
        int length = text.length();

        for (int i = 0; i < length; i++) {
            float ratio = (length == 1) ? 0 : (float) i / (float) (length - 1);
            int r = (int) (palette.start().red() * (1 - ratio) + palette.end().red() * ratio);
            int g = (int) (palette.start().green() * (1 - ratio) + palette.end().green() * ratio);
            int b = (int) (palette.start().blue() * (1 - ratio) + palette.end().blue() * ratio);

            messageText.text(String.valueOf(text.charAt(i))).color(r, g, b);
            if (bold) messageText.bold();
        }
        return messageText.build();
    }

    private static String componentToPlainText(@NotNull Component component) {
        StringBuilder sb = new StringBuilder();
        if (component instanceof TextComponent) {
            sb.append(((TextComponent) component).content());
        }
        for (Component child : component.children()) {
            sb.append(componentToPlainText(child));
        }
        return sb.toString();
    }
}

