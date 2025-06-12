package brcomkassin.dungeonWeapons.utils;

import java.text.DecimalFormat;

public class KDecimalFormatUtil {

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public static String format(double value) {
        return df.format(value);
    }

}
