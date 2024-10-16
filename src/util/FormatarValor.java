package util;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatarValor {

    public static String formatarMoeda(double valor) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        return formatter.format(valor);
    }
}
