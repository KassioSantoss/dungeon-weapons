package brcomkassin.dungeonWeapons.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class KCooldownManagerUtils {

    private static final Map<CooldownKey, Long> COOLDOWNS = new HashMap<>();

    public enum CooldownType {
        ABILITY,
        ITEM,
        BUFF,
        SKILL,
        CUSTOM
    }

    public record CooldownKey(UUID playerId, CooldownType type, String identifier) {
    }

    public static void apply(UUID playerId, CooldownType type, String identifier, long durationMillis) {
        COOLDOWNS.put(new CooldownKey(playerId, type, identifier), System.currentTimeMillis() + durationMillis);
    }

    public static boolean isOnCooldown(UUID playerId, CooldownType type, String identifier) {
        Long expiry = COOLDOWNS.get(new CooldownKey(playerId, type, identifier));
        return expiry != null && expiry > System.currentTimeMillis();
    }

    public static long getRemaining(UUID playerId, CooldownType type, String identifier) {
        return isOnCooldown(playerId, type, identifier)
                ? COOLDOWNS.get(new CooldownKey(playerId, type, identifier)) - System.currentTimeMillis()
                : 0;
    }

    public static String getFormatted(UUID playerId, CooldownType type, String identifier) {
        return String.format("%.1fs", getRemaining(playerId, type, identifier) / 1000.0);
    }

    public static void clear(UUID playerId, CooldownType type, String identifier) {
        COOLDOWNS.remove(new CooldownKey(playerId, type, identifier));
    }

    public static void clearAll(UUID playerId) {
        COOLDOWNS.keySet().removeIf(key -> key.playerId.equals(playerId));
    }
}

