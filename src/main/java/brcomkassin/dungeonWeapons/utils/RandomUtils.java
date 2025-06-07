package brcomkassin.dungeonWeapons.utils;

import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    /**
     * Retorna verdadeiro com base na chance fornecida.
     *
     * @param chance Probabilidade entre 0 e 1 (ex: 0.16 para 16%).
     * @return true se o evento ocorrer, false caso contrário.
     */
    public static boolean chance(double chance) {
        // Se chance for >1.0, assumimos que está usando porcentagem (ex: 25.0%)
        if (chance > 1.0) chance = chance / 100.0;

        if (chance <= 0) return false;
        if (chance >= 1) return true;
        return RANDOM.nextDouble() < chance;
    }

    /**
     * Retorna um número inteiro aleatório entre min (inclusive) e max (inclusive).
     *
     * @param min Valor mínimo.
     * @param max Valor máximo.
     * @return Número aleatório entre min e max.
     */
    public static int getRandomInt(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }

    public static double getRandomDouble() {
        return RANDOM.nextDouble();
    }

    /**
     * Retorna um número decimal aleatório entre min (inclusive) e max (inclusive).
     *
     * @param min Valor mínimo.
     * @param max Valor máximo.
     * @return Número aleatório entre min e max.
     */
    public static double getRandomDouble(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }
}
