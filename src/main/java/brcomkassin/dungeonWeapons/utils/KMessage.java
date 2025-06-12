package brcomkassin.dungeonWeapons.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;

public interface KMessage {
    class Chat {
        public static void send(Player player, String... message) {
            Arrays.stream(message)
                    .map(string -> ChatColor.translateAlternateColorCodes('&', string))
                    .forEach(player::sendMessage);
        }

        public static void send(Player player, Component... component) {
            Arrays.stream(component)
                    .forEach(player::sendMessage);
        }
    }

    class ActionBar {
        public static void send(Player player, Component component) {
            player.sendActionBar(component);
        }

        public static void send(Player player, String message) {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(ChatColor.translateAlternateColorCodes('&', message)));
        }

    }

    class ChatClick {
        public static void send(Player player, String message, String hoverMessage, String command) {
            TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', hoverMessage)).create()));

            player.spigot().sendMessage(component);
        }

        public static void send(Player player, String message, String hoverMessage) {
            TextComponent component = new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', hoverMessage)).create()));

            player.spigot().sendMessage(component);
        }
    }
}