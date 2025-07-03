package brcomkassin.dungeonWeapons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import java.util.ArrayList;
import java.util.List;

public class KMessageText {

    private final List<Component> parts = new ArrayList<>();
    private MessagePart current;

    private KMessageText() {
    }

    public static KMessageText create() {
        return new KMessageText();
    }

    public KMessageText text(Component component) {
        if (current != null) {
            parts.add(current.build());
        }
        parts.add(component);
        current = null;
        return this;
    }

    public KMessageText text(String content) {
        if (current != null) {
            parts.add(current.build());
        }
        current = new MessagePart(content, this);
        return this;
    }

    public KMessageText color(int r, int g, int b) {
        if (current != null) current.color(TextColor.color(r, g, b));
        return this;
    }

    public KMessageText color(TextColor textColor) {
        if (current != null) current.color(textColor);
        return this;
    }

    public KMessageText bold() {
        if (current != null) current.bold();
        return this;
    }

    public KMessageText italic() {
        if (current != null) current.italic();
        return this;
    }

    public KMessageText underline() {
        if (current != null) current.underline();
        return this;
    }

    public KMessageText strikethrough() {
        if (current != null) current.strikethrough();
        return this;
    }

    public KMessageText hover(String hoverText) {
        if (current != null) current.hover(hoverText);
        return this;
    }

    public KMessageText click(String command) {
        if (current != null) current.click(command);
        return this;
    }

    public Component build() {
        if (current != null) {
            parts.add(current.build());
            current = null;
        }
        return Component.join(JoinConfiguration.noSeparators(), parts);
    }

    private static class MessagePart {
        private final TextComponent.Builder builder;
        private final KMessageText parent;

        public MessagePart(String text, KMessageText parent) {
            this.builder = Component.text().content(text);
            this.parent = parent;
        }

        public void color(TextColor color) {
            builder.color(color);
        }

        public void bold() {
            builder.decorate(TextDecoration.BOLD);
        }

        public void italic() {
            builder.decorate(TextDecoration.ITALIC);
        }

        public void underline() {
            builder.decorate(TextDecoration.UNDERLINED);
        }

        public void strikethrough() {
            builder.decorate(TextDecoration.STRIKETHROUGH);
        }

        public void hover(String text) {
            builder.hoverEvent(HoverEvent.showText(Component.text(text)));
        }

        public void click(String command) {
            builder.clickEvent(ClickEvent.runCommand(command));
        }

        public Component build() {
            return builder.build();
        }
    }
}

