package models;

import java.util.ArrayList;
import java.util.List;

public class ActivitePhysique {
    private int id;
    private String nomActivite, typeActivite;
    private int dureeActivite, caloriesBrules, nbSeries, nbRepSeries, poidsParSerie;
<<<<<<< HEAD
    //private String imageActivite;
=======
    private String imageActivite;
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
    private List<Objectif> objectifs;

    public ActivitePhysique() {
        this.objectifs =new ArrayList<>();
    }

    public ActivitePhysique(int id, String nomActivite, String typeActivite, int dureeActivite, int caloriesBrules, int nbSeries, int nbRepSeries, int poidsParSerie, String imageActivite) {
        this.id = id;
        this.nomActivite = nomActivite;
        this.typeActivite = typeActivite;
        this.dureeActivite = dureeActivite;
        this.caloriesBrules = caloriesBrules;
        this.nbSeries = nbSeries;
        this.nbRepSeries = nbRepSeries;
        this.poidsParSerie = poidsParSerie;
<<<<<<< HEAD
       /* this.imageActivite = imageActivite;
        this.objectifs =new ArrayList<>();
        */
    }

   public List<Objectif> getObjectifs() {
=======
        this.imageActivite = imageActivite;
        this.objectifs =new ArrayList<>();
    }

    public List<Objectif> getObjectifs() {
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
        return objectifs;
    }

    public void setObjectifs(List<Objectif> objectifs) {
        this.objectifs = objectifs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomActivite() {
        return nomActivite;
    }

    public void setNomActivite(String nomActivite) {
        this.nomActivite = nomActivite;
    }

    public String getTypeActivite() {
        return typeActivite;
    }

    public void setTypeActivite(String typeActivite) {
        this.typeActivite = typeActivite;
    }

    public int getDureeActivite() {
        return dureeActivite;
    }

    public void setDureeActivite(int dureeActivite) {
        this.dureeActivite = dureeActivite;
    }

    public int getCaloriesBrules() {
        return caloriesBrules;
    }

    public void setCaloriesBrules(int caloriesBrules) {
        this.caloriesBrules = caloriesBrules;
    }

    public int getNbSeries() {
        return nbSeries;
    }

    public void setNbSeries(int nbSeries) {
        this.nbSeries = nbSeries;
    }

    public int getNbRepSeries() {
        return nbRepSeries;
    }

    public void setNbRepSeries(int nbRepSeries) {
        this.nbRepSeries = nbRepSeries;
    }

    public int getPoidsParSerie() {
        return poidsParSerie;
    }

    public void setPoidsParSerie(int poidsParSerie) {
        this.poidsParSerie = poidsParSerie;
    }

<<<<<<< HEAD
   /* public String getImageActivite() {
=======
    public String getImageActivite() {
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
        return imageActivite;
    }

    public void setImageActivite(String imageActivite) {
        this.imageActivite = imageActivite;
    }

<<<<<<< HEAD

    */
=======
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
    @Override
    public String toString() {
        return "ActivitePhysique{" +
                "id=" + id +
                ", nomActivite='" + nomActivite + '\'' +
                ", typeActivite='" + typeActivite + '\'' +
                ", dureeActivite=" + dureeActivite +
                ", caloriesBrules=" + caloriesBrules +
                ", nbSeries=" + nbSeries +
                ", nbRepSeries=" + nbRepSeries +
                ", poidsParSerie=" + poidsParSerie +
<<<<<<< HEAD
              /* ", imageActivite='" + imageActivite + '\'' +*/
=======
                ", imageActivite='" + imageActivite + '\'' +
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
                '}';
    }
}
