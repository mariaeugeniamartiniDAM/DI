package vista;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.UIManager;

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
    
    public static void aplicarTextosSwing() {
        UIManager.put("OptionPane.yesButtonText", get("dialog.si"));
        UIManager.put("OptionPane.noButtonText", get("dialog.no"));
        UIManager.put("OptionPane.okButtonText", get("dialog.dacord"));
        UIManager.put("OptionPane.cancelButtonText", get("dialog.cancelar"));
    }

    /**
     * Substitueix {0}, {1}, ... pel valor corresponent.
     *
     * No s'utilitza MessageFormat perquè els apòstrofs dels textos catalans
     * fan que MessageFormat interpreti com a text
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
