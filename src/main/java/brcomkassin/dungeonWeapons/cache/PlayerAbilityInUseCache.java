package brcomkassin.dungeonWeapons.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerAbilityInUseCache {

    private static final Map<String, List<String>> playerAbilityInUseCache = new HashMap<>();

    public static void add(String abilityName, String name) {
        playerAbilityInUseCache.computeIfAbsent(abilityName, k -> new ArrayList<>()).add(name);
    }

    public static List<String> get(String abilityName) {
        return playerAbilityInUseCache.getOrDefault(abilityName, List.of());
    }

    public static void remove(String abilityName, String name) {
        List<String> list = playerAbilityInUseCache.get(abilityName);
        if (list != null) {
            list.remove(name);
            if (list.isEmpty()) {
                playerAbilityInUseCache.remove(abilityName);
            }
        }
    }

    public static boolean contains(String abilityName, String name) {
        return playerAbilityInUseCache.containsKey(abilityName) && playerAbilityInUseCache.get(abilityName).contains(name);
    }

}
