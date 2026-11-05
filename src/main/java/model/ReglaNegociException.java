package model;

/**
 * Excepció de regla de negoci. Porta una clau que la interfície tradueix
 * mitjançant Bundle_ca.
 */
public class ReglaNegociException extends Exception {

    private static final long serialVersionUID = 1L;

    private final String clau;

    public ReglaNegociException(String clau) {
        super(clau);
        this.clau = clau;
    }

    public String getClau() {
        return clau;
    }
}
