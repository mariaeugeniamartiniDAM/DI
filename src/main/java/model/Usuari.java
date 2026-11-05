package model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Usuari de la biblioteca de videojocs.
 */
public class Usuari {

    private final String nom;
    private final String cognom1;
    private final String cognom2;
    private final String dni;
    private final String telefon;
    private final LocalDate dataRegistre;

    public Usuari(String nom, String cognom1, String cognom2, String dni, String telefon, LocalDate dataRegistre) {
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.dni = dni;
        this.telefon = telefon;
        this.dataRegistre = dataRegistre;
    }

    public String getNom() {
        return nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public String getDni() {
        return dni;
    }

    public String getTelefon() {
        return telefon;
    }

    public LocalDate getDataRegistre() {
        return dataRegistre;
    }

    public String getNomComplet() {
        if (cognom2 == null || cognom2.trim().isEmpty()) {
            return nom + " " + cognom1;
        }
        return nom + " " + cognom1 + " " + cognom2;
    }

    @Override
    public String toString() {
        return getNomComplet();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Usuari)) {
            return false;
        }
        Usuari altre = (Usuari) obj;
        return dni != null && dni.equalsIgnoreCase(altre.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni == null ? null : dni.toUpperCase());
    }
}
