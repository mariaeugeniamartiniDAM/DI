package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Videojoc amb estoc independent per a cada plataforma.
 */
public class Joc {

    private final String nom;
    private final Genere genere;
    private final Map<Plataforma, Integer> estoc;

    public Joc(String nom, Genere genere, Map<Plataforma, Integer> estoc) {
        this.nom = nom;
        this.genere = genere;
        this.estoc = new EnumMap<>(Plataforma.class);
        if (estoc != null) {
            this.estoc.putAll(estoc);
        }
    }

    public String getNom() {
        return nom;
    }

    public Genere getGenere() {
        return genere;
    }

    /**
     * Retorna una còpia de només lectura de l'estoc per plataforma.
     */
    public Map<Plataforma, Integer> getEstoc() {
        return Collections.unmodifiableMap(estoc);
    }

    /**
     * Indica si el joc s'ofereix en aquesta plataforma (encara que ara mateix tingui 0 unitats).
     */
    public boolean tePlataforma(Plataforma plataforma) {
        return estoc.containsKey(plataforma);
    }

    public int getEstoc(Plataforma plataforma) {
        return estoc.getOrDefault(plataforma, 0);
    }

    public List<Plataforma> getPlataformes() {
        return new ArrayList<>(estoc.keySet());
    }

    /**
     * Resta una unitat de l'estoc de la plataforma indicada.
     */
    public void reduirEstoc(Plataforma plataforma) {
        int actual = getEstoc(plataforma);
        if (actual <= 0) {
            throw new IllegalStateException("No hi ha unitats disponibles a " + plataforma.getNom());
        }
        estoc.put(plataforma, actual - 1);
    }

    /**
     * Torna una unitat a l'estoc de la plataforma indicada.
     */
    public void augmentarEstoc(Plataforma plataforma) {
        estoc.put(plataforma, getEstoc(plataforma) + 1);
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Joc)) {
            return false;
        }
        Joc altre = (Joc) obj;
        return nom != null && nom.equalsIgnoreCase(altre.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom == null ? null : nom.toLowerCase());
    }
}
