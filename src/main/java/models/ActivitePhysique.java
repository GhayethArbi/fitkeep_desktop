package models;

import java.util.ArrayList;
import java.util.List;

public class ActivitePhysique {
    private Integer id;
    private String nomActivite, typeActivite;
    private Integer dureeActivite, caloriesBrules, nbSeries, nbRepSeries, poidsParSerie;
    private String imageActivite;
    private List<Objectif> objectifs;

    public ActivitePhysique() {
        this.objectifs =new ArrayList<>();
    }

    public ActivitePhysique(Integer id, String nomActivite, String typeActivite, Integer dureeActivite, Integer caloriesBrules, Integer nbSeries, Integer nbRepSeries, Integer poidsParSerie, String imageActivite) {
        this.id = id;
        this.nomActivite = nomActivite;
        this.typeActivite = typeActivite;
        this.dureeActivite = dureeActivite;
        this.caloriesBrules = caloriesBrules;
        this.nbSeries = nbSeries;
        this.nbRepSeries = nbRepSeries;
        this.poidsParSerie = poidsParSerie;
        this.imageActivite = imageActivite;
        this.objectifs =new ArrayList<>();
    }

    public List<Objectif> getObjectifs() {
        return objectifs;
    }

    public void setObjectifs(List<Objectif> objectifs) {
        this.objectifs = objectifs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getDureeActivite() {
        return dureeActivite;
    }

    public void setDureeActivite(Integer dureeActivite) {
        this.dureeActivite = dureeActivite;
    }

    public Integer getCaloriesBrules() {
        return caloriesBrules;
    }

    public void setCaloriesBrules(Integer caloriesBrules) {
        this.caloriesBrules = caloriesBrules;
    }

    public Integer getNbSeries() {
        return nbSeries;
    }

    public void setNbSeries(Integer nbSeries) {
        this.nbSeries = nbSeries;
    }

    public Integer getNbRepSeries() {
        return nbRepSeries;
    }

    public void setNbRepSeries(Integer nbRepSeries) {
        this.nbRepSeries = nbRepSeries;
    }

    public Integer getPoidsParSerie() {
        return poidsParSerie;
    }

    public void setPoidsParSerie(Integer poidsParSerie) {
        this.poidsParSerie = poidsParSerie;
    }

    public String getImageActivite() {
        return imageActivite;
    }

    public void setImageActivite(String imageActivite) {
        this.imageActivite = imageActivite;
    }



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
            ", imageActivite='" + imageActivite + '\'' +
            '}';
    }
}
