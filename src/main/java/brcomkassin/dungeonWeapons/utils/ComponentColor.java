package brcomkassin.dungeonWeapons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ComponentColor {

    private final TextComponent.Builder builder;
    private final List<Component> components = new ArrayList<>();

    private ComponentColor(String text) {
        this.builder = Component.text().content(text);
    }

    public static ComponentColor of() {
        return new ComponentColor("");
    }

    public static ComponentColor text(String text) {
        text = text.replace("&", "§");
        return new ComponentColor(text);
    }

    public ComponentColor text(String text, Consumer<TextComponent.Builder> style) {
        TextComponent.Builder builder = Component.text().content(text);
        style.accept(builder);
        components.add(builder.build());
        return this;
    }

    public ComponentColor color(int r, int g, int b) {
        builder.color(TextColor.color(r, g, b));
        return this;
    }

    public ComponentColor bold() {
        builder.decorate(TextDecoration.BOLD);
        return this;
    }

    public ComponentColor italic() {
        builder.decorate(TextDecoration.ITALIC);
        return this;
    }

    public ComponentColor clickEvent(ClickEvent.Action action, String value) {
        builder.clickEvent(ClickEvent.clickEvent(action, value));
        return this;
    }

    public ComponentColor hoverText(String text) {
        builder.hoverEvent(HoverEvent.showText(Component.text(text)));
        return this;
    }

    public Component build() {
        return builder.build();
    }

}