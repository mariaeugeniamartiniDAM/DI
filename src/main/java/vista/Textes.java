package vista;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Accés centralitzat al Bundle_ca (locale ca-ES).
 */
public final class Textes {

    public static final Locale LOCALE_CA_ES = Locale.forLanguageTag("ca-ES");

    private static final ResourceBundle BUNDLE
            = ResourceBundle.getBundle("Bundle_ca", LOCALE_CA_ES);

    private Textes() {
    }

    public static String get(String clau) {
        try {
            return BUNDLE.getString(clau);
        } catch (MissingResourceException ex) {
            return clau;
        }
    }

    /**
     * Substitueix {0}, {1}, ... pel valor corresponent.
     *
     * No s'utilitza MessageFormat perquè els apòstrofs dels textos catalans
     * (per exemple "l'usuari") fan que MessageFormat interpreti com a text
     * literal i no substitueixi els paràmetres.
     */
    public static String format(String clau, Object... arguments) {
        String text = get(clau);
        for (int i = 0; i < arguments.length; i++) {
            String valor = arguments[i] == null ? "" : arguments[i].toString();
            text = text.replace("{" + i + "}", valor);
        }
        return text;
    }
}
