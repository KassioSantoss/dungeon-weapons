package brcomkassin.dungeonWeapons.utils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownUtils {

    // Mapa de cooldowns agrupado por chave (ex: "fireball:playerUUID")
    private static final Map<String, Long> cooldowns = new ConcurrentHashMap<>();

    /**
     * Define um cooldown para uma chave única.
     *
     * @param key identificador único (ex: "fireball:playerUUID")
     * @param durationMillis duração em milissegundos
     */
    public static void setCooldown(String key, long durationMillis) {
        cooldowns.put(key, System.currentTimeMillis() + durationMillis);
    }

    /**
     * Retorna true se o cooldown estiver ativo para a chave.
     *
     * @param key identificador único
     * @return true se ainda está em cooldown
     */
    public static boolean isOnCooldown(String key) {
        return cooldowns.containsKey(key) && cooldowns.get(key) > System.currentTimeMillis();
    }

    /**
     * Retorna o tempo restante em milissegundos para o cooldown.
     *
     * @param key identificador único
     * @return tempo restante ou 0 se expirado
     */
    public static long getRemaining(String key) {
        return isOnCooldown(key) ? cooldowns.get(key) - System.currentTimeMillis() : 0L;
    }

    /**
     * Remove o cooldown da chave.
     *
     * @param key identificador único
     */
    public static void removeCooldown(String key) {
        cooldowns.remove(key);
    }

    /**
     * Retorna o tempo restante formatado em segundos com 1 casa decimal.
     *
     * @param key identificador único
     * @return ex: "1.2s", ou "0.0s" se não estiver em cooldown
     */
    public static String getFormatted(String key) {
        return String.format("%.1fs", getRemaining(key) / 1000.0);
    }

    /**
     * Útil para casos com player.
     *
     * @param player UUID do jogador
     * @param action Ação identificadora (ex: "fireball", "dash")
     */
    public static String createKey(UUID player, String action) {
        return action.toLowerCase() + ":" + player.toString();
    }
}
