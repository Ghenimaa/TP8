
package champollion;

import java.util.Date;

public class Intervention {
    private Date debut;
    private int duree;
    private Boolean annulee = false;
    private Salle salle;
    private TypeIntervention type;
    private Enseignant enseignant;
    private UE ue;

    public Intervention(Date debut, int duree, Salle salle, TypeIntervention type, Enseignant enseignant, UE ue) {
        this.debut = debut;
        this.duree = duree;
        this.salle = salle;
        this.type = type;
        this.enseignant = enseignant;
        this.ue = ue;
    }

    public Date getDebut() {
        return debut;
    }

    public int getDuree() {
        return duree;
    }

    public Boolean getAnnulee() {
        return annulee;
    }

    public Salle getSalle() {
        return salle;
    }

    public TypeIntervention getType() {
        return type;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public UE getUe() {
        return ue;
    }
}
