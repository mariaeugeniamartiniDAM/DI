package model;

/**
 * Plataformes suportades per l'aplicació.
 * L'ordre és el que es mostrarà als formularis.
 */
public enum Plataforma {
    SWITCH2("Switch 2"),
    XBOX("Xbox"),
    PS5("PS5"),
    PC("PC");

    private final String nom;

    Plataforma(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom;
    }
}
