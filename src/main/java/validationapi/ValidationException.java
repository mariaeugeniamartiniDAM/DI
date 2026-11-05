package validationapi;

/**
 * Excepció llançada per ValidationAPI.
 * La clau identifica l'error i la interfície la tradueix amb Bundle_ca.
 */
public class ValidationException extends Exception {

    private final String clau;

    public ValidationException(String clau) {
        super(clau);
        this.clau = clau;
    }

    public String getClau() {
        return clau;
    }
}
