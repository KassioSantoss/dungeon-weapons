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

public class MessageText {

    private final List<Component> parts = new ArrayList<>();
    private MessagePart current;

    private MessageText() {
    }

    public static MessageText create() {
        return new MessageText();
    }

    public MessageText text(Component component) {
        if (current != null) {
            parts.add(current.build());
        }
        parts.add(component);
        current = null;
        return this;
    }


    public MessageText text(String content) {
        if (current != null) {
            parts.add(current.build());
        }
        current = new MessagePart(content, this);
        return this;
    }

    public MessageText color(int r, int g, int b) {
        current.color(TextColor.color(r, g, b));
        return this;
    }

    public MessageText bold() {
        current.bold();
        return this;
    }

    public MessageText italic() {
        current.italic();
        return this;
    }

    public MessageText underline() {
        current.underline();
        return this;
    }

    public MessageText strikethrough() {
        current.strikethrough();
        return this;
    }

    public MessageText hover(String hoverText) {
        current.hover(hoverText);
        return this;
    }

    public MessageText click(String command) {
        current.click(command);
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
        private final MessageText parent;

        public MessagePart(String text, MessageText parent) {
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

