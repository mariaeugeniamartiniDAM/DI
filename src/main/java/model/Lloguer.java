package model;

import java.time.LocalDate;

/**
 * Lloguer d'un joc per part d'un usuari.
 *
 * La data de tornada prevista s'assigna automàticament a 7 dies vista.
 * El lloguer només pot passar a RETORNAT una vegada.
 */
public class Lloguer {

    private final int numeroRegistre;
    private final Usuari usuari;
    private final Joc joc;
    private final Plataforma plataforma;
    private final LocalDate dataInici;
    private final LocalDate dataTornadaPrevista;

    private LocalDate dataRetornEfectiu;
    private EstatLloguer estat;

    public Lloguer(int numeroRegistre, Usuari usuari, Joc joc, Plataforma plataforma,
            LocalDate dataInici, LocalDate dataTornadaPrevista) {
        this.numeroRegistre = numeroRegistre;
        this.usuari = usuari;
        this.joc = joc;
        this.plataforma = plataforma;
        this.dataInici = dataInici;
        this.dataTornadaPrevista = dataTornadaPrevista;
        this.estat = EstatLloguer.ACTIU;
    }

    public int getNumeroRegistre() {
        return numeroRegistre;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public Joc getJoc() {
        return joc;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public LocalDate getDataInici() {
        return dataInici;
    }

    public LocalDate getDataTornadaPrevista() {
        return dataTornadaPrevista;
    }

    public LocalDate getDataRetornEfectiu() {
        return dataRetornEfectiu;
    }

    public EstatLloguer getEstat() {
        return estat;
    }

    public boolean isActiu() {
        return estat == EstatLloguer.ACTIU;
    }

    public boolean isRetornat() {
        return estat == EstatLloguer.RETORNAT;
    }

    /**
     * Un lloguer està vençut si continua actiu i la data actual supera la data prevista de tornada.
     */
    public boolean isVencut() {
        return isActiu() && LocalDate.now().isAfter(dataTornadaPrevista);
    }

    /**
     * Marca el lloguer com a retornat. Només es pot fer si encara està actiu.
     */
    public void marcarRetornat(LocalDate dataRetorn) {
        if (!isActiu()) {
            throw new IllegalStateException("El lloguer ja s'ha retornat.");
        }
        this.estat = EstatLloguer.RETORNAT;
        this.dataRetornEfectiu = dataRetorn;
    }

    @Override
    public String toString() {
        return "Lloguer " + numeroRegistre + " - " + joc.getNom() + " - " + usuari.getNomComplet();
    }
}
