package brcomkassin.dungeonWeapons.utils;

public class RomanNumber {
    private static final String[] roman = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

    public static String toRoman(int number) {
        if (number >= 1 && number <= 10) return roman[number];
        return String.valueOf(number);
    }
}
