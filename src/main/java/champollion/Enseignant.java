package champollion;
import java.util.HashSet;


/**
 * Un enseignant est caractérisé par les informations suivantes : son nom, son adresse email, et son service prévu,
 * et son emploi du temps.
 */
public class Enseignant extends Personne {
    private HashSet<Intervention> intervensions = new HashSet<Intervention>();
    private HashSet<ServicePrevu> servicePrevus = new HashSet<ServicePrevu>();

    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

    public Enseignant(String nom, String email) {
        super(nom, email);
    }


    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
        int resultat = 0;
        for (ServicePrevu s : servicePrevus) {
            resultat = resultat + heuresPrevuesPourUE(s.getUe());
        }
        return resultat;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
        double resultat = 0.0;
        for (ServicePrevu s : servicePrevus) {
            if (s.getUe().equals(ue)) {
                resultat = resultat +  equivalentTD(TypeIntervention.CM, s.getVolumeCM())
                        + equivalentTD(TypeIntervention.TD, s.getVolumeTD())
                        + equivalentTD(TypeIntervention.TP, s.getVolumeTP());
            }
        }
        return (int) Math.round(resultat);
    }


    public double equivalentTD(TypeIntervention type, int val) {
        double resultat = 0.0;
        if (type.equals(TypeIntervention.CM)) {
            resultat = resultat + val * 1.5;
        } else if (type.equals(TypeIntervention.TD)) {
            resultat = resultat + val;
        } else if (type.equals(TypeIntervention.TP)) {
            resultat = resultat + val * 0.75;
        }
        return resultat;
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     *
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magistral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
        // TODO: Implémenter cette méthode
        if (volumeCM < 0 || volumeTP < 0 || volumeTD < 0) {
            throw new IllegalArgumentException("Le volume est positif");
        }
        boolean ueExist = false;

        for (ServicePrevu s : servicePrevus) {
            if (s.getUe().equals(ue)) {
                ueExist = true;
                s.addToCM(volumeCM);
                s.addToTD(volumeTD);
                s.addToTP(volumeTP);
            }
        }
        if (ueExist == false)  {
            servicePrevus.add(new ServicePrevu(volumeCM, volumeTD, volumeTP, ue));
        }
    }

    public Boolean enSousService() {
        if (heuresPrevues() <= 192) {
            return true;
        }
        return false;
    }

    public ServicePrevu getServicePrevuFromUE(UE ue) {
        for (ServicePrevu s : servicePrevus) {
            if (s.getUe().equals(ue)) {
                return s;
            }
        }
        return null;
    }

    public HashSet<ServicePrevu> getServicePrevus() {
        return servicePrevus;
    }

    public void ajouteIntervention(Intervention inter) {
        if (getServicePrevuFromUE(inter.getUe()) == null) {
            throw new IllegalArgumentException("L'UE ne fait pas partie des enseignements");
        }
        intervensions.add(inter);
    }


    public int resteAPlanifier(UE ue, TypeIntervention type) {
        int planifiee = 0;
        for (Intervention i : intervensions) {
            if (i.getUe().equals(ue) && i.getType().equals(type)) {
                planifiee = planifiee + i.getDuree();
            }
        }
        int nombreTotal = getServicePrevuFromUE(ue).getServiceType(type);
        return nombreTotal - planifiee;
    }

    public HashSet<Intervention> getIntervensions() {
        return intervensions;
    }
}





