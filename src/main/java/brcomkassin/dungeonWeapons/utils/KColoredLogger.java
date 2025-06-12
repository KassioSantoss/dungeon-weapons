package brcomkassin.dungeonWeapons.utils;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;

public class KColoredLogger {

    private static final Map<Character, String> COLOR_CODES = new HashMap<>();

    static {
        COLOR_CODES.put('0', "\u001B[30m"); // Preto
        COLOR_CODES.put('1', "\u001B[34m"); // Azul Escuro
        COLOR_CODES.put('2', "\u001B[32m"); // Verde Escuro
        COLOR_CODES.put('3', "\u001B[36m"); // Ciano
        COLOR_CODES.put('4', "\u001B[31m"); // Vermelho
        COLOR_CODES.put('5', "\u001B[35m"); // Roxo
        COLOR_CODES.put('6', "\u001B[33m"); // Laranja
        COLOR_CODES.put('7', "\u001B[37m"); // Cinza Claro
        COLOR_CODES.put('8', "\u001B[90m"); // Cinza Escuro
        COLOR_CODES.put('9', "\u001B[94m"); // Azul Claro
        COLOR_CODES.put('a', "\u001B[92m"); // Verde Claro
        COLOR_CODES.put('b', "\u001B[96m"); // Ciano Claro
        COLOR_CODES.put('c', "\u001B[91m"); // Vermelho Claro
        COLOR_CODES.put('d', "\u001B[95m"); // Rosa
        COLOR_CODES.put('e', "\u001B[93m"); // Amarelo
        COLOR_CODES.put('f', "\u001B[97m"); // Branco
        COLOR_CODES.put('k', "\u001B[5m");  // Piscando (não suportado em todos os consoles)
        COLOR_CODES.put('l', "\u001B[1m");  // Negrito
        COLOR_CODES.put('m', "\u001B[9m");  // Tachado
        COLOR_CODES.put('n', "\u001B[4m");  // Sublinhado
        COLOR_CODES.put('o', "\u001B[3m");  // Itálico (não suportado em todos os consoles)
        COLOR_CODES.put('r', "\u001B[0m");  // Resetar
    }

    public static void info(String message) {
        String coloredMessage = translateColorCodes(message) + "\u001B[0m";
         Bukkit.getLogger().info(coloredMessage);
    }

    public static void error(String message) {
        String coloredMessage = "\u001B[31m" + translateColorCodes(message) + "\u001B[0m";
        Bukkit.getLogger().info(coloredMessage);
    }

    private static String translateColorCodes(String message) {
        StringBuilder result = new StringBuilder();
        char[] chars = message.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '&' && i + 1 < chars.length) {
                char code = chars[i + 1];
                if (COLOR_CODES.containsKey(code)) {
                    result.append(COLOR_CODES.get(code));
                    i++;
                    continue;
                }
            }
            result.append(chars[i]);
        }
        return result.toString();
    }

}
