package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Magatzem en memòria de tota l'aplicació.
 *
 * És l'única font de veritat per a usuaris, jocs, lloguers i estoc.
 * La interfície només ha de cridar aquests mètodes i refrescar-se.
 */
public final class GestorDades {

    private static final Logger LOG = Logger.getLogger(GestorDades.class.getName());
    private static GestorDades instancia;

    private final List<Usuari> usuaris = new ArrayList<>();
    private final List<Joc> jocs = new ArrayList<>();
    private final List<Lloguer> lloguers = new ArrayList<>();

    private int seguentNumeroLloguer = 1;

    private GestorDades() {
        inicialitzarDadesProva();
    }

    public static synchronized GestorDades getInstancia() {
        if (instancia == null) {
            instancia = new GestorDades();
        }
        return instancia;
    }

    /**
     * Dades inicials obligatòries:
     *  - 2 usuaris
     *  - Joc 1 disponible a PS5 i Switch 2
     *  - Joc 2 disponible a Xbox i PC
     *  - 2 lloguers inicials (un per usuari), amb l'estoc descomptat
     *
     * El primer lloguer es deixa vençut a propòsit per poder comprovar
     * la fila vermella des del primer moment.
     */
    private void inicialitzarDadesProva() {
        LocalDate avui = LocalDate.now();

        Usuari marc = new Usuari("Marc", "Rosselló", "Puig", "12345678A", "600111222", avui);
        Usuari laia = new Usuari("Laia", "Serra", "Vidal", "87654321B", "600333444", avui);
        usuaris.add(marc);
        usuaris.add(laia);

        Map<Plataforma, Integer> estocZelda = new EnumMap<>(Plataforma.class);
        estocZelda.put(Plataforma.PS5, 2);
        estocZelda.put(Plataforma.SWITCH2, 3);
        Joc zelda = new Joc("The Legend of Zelda", Genere.AVENTURA, estocZelda);
        jocs.add(zelda);

        Map<Plataforma, Integer> estocForza = new EnumMap<>(Plataforma.class);
        estocForza.put(Plataforma.XBOX, 2);
        estocForza.put(Plataforma.PC, 2);
        Joc forza = new Joc("Forza Horizon", Genere.ESPORTS, estocForza);
        jocs.add(forza);

        // Lloguer 1: Marc + Zelda + Switch 2. Va començar fa 10 dies i havia de tornar fa 3.
        zelda.reduirEstoc(Plataforma.SWITCH2);
        lloguers.add(new Lloguer(seguentNumeroLloguer++, marc, zelda, Plataforma.SWITCH2,
                avui.minusDays(10), avui.minusDays(3)));

        // Lloguer 2: Laia + Forza + PC. Actiu i dins de termini.
        forza.reduirEstoc(Plataforma.PC);
        lloguers.add(new Lloguer(seguentNumeroLloguer++, laia, forza, Plataforma.PC,
                avui, avui.plusDays(7)));

        LOG.info("Dades inicials carregades: 2 usuaris, 2 jocs i 2 lloguers.");
    }

    public List<Usuari> getUsuaris() {
        return Collections.unmodifiableList(usuaris);
    }

    public List<Joc> getJocs() {
        return Collections.unmodifiableList(jocs);
    }

    public List<Lloguer> getLloguers() {
        return Collections.unmodifiableList(lloguers);
    }

    /**
     * Lloguers ordenats pel número de registre de més gran a més petit.
     */
    public List<Lloguer> getLloguersOrdenatsDesc() {
        List<Lloguer> copia = new ArrayList<>(lloguers);
        copia.sort(Comparator.comparingInt(Lloguer::getNumeroRegistre).reversed());
        return copia;
    }

    /**
     * Només els lloguers actius, ordenats del més antic al més recent.
     */
    public List<Lloguer> getLloguersActius() {
        List<Lloguer> actius = new ArrayList<>();
        for (Lloguer lloguer : lloguers) {
            if (lloguer.isActiu()) {
                actius.add(lloguer);
            }
        }
        actius.sort(Comparator.comparingInt(Lloguer::getNumeroRegistre));
        return actius;
    }

    public int getSeguentNumeroLloguer() {
        return seguentNumeroLloguer;
    }

    public void afegirUsuari(Usuari usuari) throws ReglaNegociException {
        if (usuari == null) {
            throw new ReglaNegociException("error.validacio.generic");
        }
        for (Usuari existent : usuaris) {
            if (existent.getDni().equalsIgnoreCase(usuari.getDni())) {
                throw new ReglaNegociException("error.usuari.dni.duplicat");
            }
        }
        usuaris.add(usuari);
        LOG.info(() -> "Usuari creat: " + usuari.getNomComplet());
    }

    public void esborrarUsuari(Usuari usuari) throws ReglaNegociException {
        if (usuari == null || !usuaris.contains(usuari)) {
            throw new ReglaNegociException("error.usuari.no.trobat");
        }
        if (teLloguersActius(usuari)) {
            throw new ReglaNegociException("error.usuari.amb.lloguers");
        }
        usuaris.remove(usuari);
        LOG.info(() -> "Usuari esborrat: " + usuari.getNomComplet());
    }

    public boolean teLloguersActius(Usuari usuari) {
        for (Lloguer lloguer : lloguers) {
            if (lloguer.isActiu() && lloguer.getUsuari().equals(usuari)) {
                return true;
            }
        }
        return false;
    }

    public void afegirJoc(String nom, Genere genere, Map<Plataforma, Integer> estoc)
            throws ReglaNegociException {
        if (nom == null || nom.trim().isEmpty()) {
            throw new ReglaNegociException("error.nom.joc.obligatori");
        }
        if (genere == null) {
            throw new ReglaNegociException("error.genere.obligatori");
        }
        if (estoc == null || estoc.isEmpty()) {
            throw new ReglaNegociException("error.plataforma.obligatoria");
        }

        String nomNeteja = nom.trim();
        for (Joc existent : jocs) {
            if (existent.getNom().equalsIgnoreCase(nomNeteja)) {
                throw new ReglaNegociException("error.joc.duplicat");
            }
        }

        jocs.add(new Joc(nomNeteja, genere, estoc));
        LOG.info(() -> "Joc creat: " + nomNeteja);
    }

    public void esborrarJoc(Joc joc) throws ReglaNegociException {
        if (joc == null || !jocs.contains(joc)) {
            throw new ReglaNegociException("error.joc.no.trobat");
        }
        if (teLloguersActius(joc)) {
            throw new ReglaNegociException("error.joc.amb.lloguers");
        }
        jocs.remove(joc);
        LOG.info(() -> "Joc esborrat: " + joc.getNom());
    }

    public boolean teLloguersActius(Joc joc) {
        for (Lloguer lloguer : lloguers) {
            if (lloguer.isActiu() && lloguer.getJoc().equals(joc)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registra un lloguer:
     *  - valida usuari, joc, plataforma i estoc
     *  - impedeix duplicar un lloguer actiu del mateix usuari/joc/plataforma
     *  - descompta una unitat
     *  - assigna número de registre consecutiu i dates automàtiques
     */
    public Lloguer registrarLloguer(Usuari usuari, Joc joc, Plataforma plataforma)
            throws ReglaNegociException {
        if (usuari == null) {
            throw new ReglaNegociException("error.seleccio.usuari");
        }
        if (joc == null) {
            throw new ReglaNegociException("error.seleccio.joc");
        }
        if (plataforma == null) {
            throw new ReglaNegociException("error.seleccio.plataforma");
        }
        if (!usuaris.contains(usuari)) {
            throw new ReglaNegociException("error.usuari.no.trobat");
        }
        if (!jocs.contains(joc)) {
            throw new ReglaNegociException("error.joc.no.trobat");
        }
        if (!joc.tePlataforma(plataforma)) {
            throw new ReglaNegociException("error.plataforma.no.disponible");
        }
        if (joc.getEstoc(plataforma) <= 0) {
            throw new ReglaNegociException("error.sense.estoc");
        }

        for (Lloguer actiu : lloguers) {
            if (actiu.isActiu()
                    && actiu.getUsuari().equals(usuari)
                    && actiu.getJoc().equals(joc)
                    && actiu.getPlataforma() == plataforma) {
                throw new ReglaNegociException("error.lloguer.duplicat");
            }
        }

        joc.reduirEstoc(plataforma);

        LocalDate avui = LocalDate.now();
        Lloguer lloguer = new Lloguer(seguentNumeroLloguer++, usuari, joc, plataforma,
                avui, avui.plusDays(7));
        lloguers.add(lloguer);

        LOG.info(() -> "Lloguer registrat: " + lloguer);
        return lloguer;
    }

    /**
     * Registra el retorn d'un lloguer actiu i restaura l'estoc.
     */
    public void registrarRetorn(Lloguer lloguer) throws ReglaNegociException {
        if (lloguer == null || !lloguers.contains(lloguer)) {
            throw new ReglaNegociException("error.lloguer.no.trobat");
        }
        if (!lloguer.isActiu()) {
            throw new ReglaNegociException("error.lloguer.ja.retornat");
        }

        lloguer.marcarRetornat(LocalDate.now());
        lloguer.getJoc().augmentarEstoc(lloguer.getPlataforma());

        LOG.info(() -> "Retorn registrat: " + lloguer);
    }
}
