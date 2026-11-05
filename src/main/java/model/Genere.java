package model;

/**
 * Gèneres disponibles per als videojocs.
 */
public enum Genere {
    ACCIO("Acció"),
    AVENTURA("Aventura"),
    RPG("RPG"),
    ESPORTS("Esports"),
    SIMULACIO("Simulació"),
    TERROR("Terror"),
    ESTRATEGIA("Estratègia"),
    PLATAFORMES("Plataformes"),
    ALTRES("Altres");

    private final String nom;

    Genere(String nom) {
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
