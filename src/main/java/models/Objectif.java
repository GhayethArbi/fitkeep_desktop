package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Objectif {
    private Integer id;
    private String nomObjectif;
    private Date dateObjectif;
    private Integer totalCalories;
    private Integer totalDuree;
    private String note;
    private List<ActivitePhysique> activites;

    public Objectif() {
        this.activites=new ArrayList<>();
    }


    public Objectif(Integer id, String nomObjectif, Date dateObjectif, Integer totalCalories, Integer totalDuree, String note) {
        this.id = id;
        this.nomObjectif = nomObjectif;
        this.dateObjectif = dateObjectif;
        this.totalCalories = totalCalories;
        this.totalDuree = totalDuree;
        this.note = note;
        this.activites=new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomObjectif() {
        return nomObjectif;
    }

    public void setNomObjectif(String nomObjectif) {
        this.nomObjectif = nomObjectif;
    }

    public java.sql.Date getDateObjectif() {
        return (java.sql.Date) dateObjectif;
    }

    public void setDateObjectif(Date dateObjectif) {
        this.dateObjectif = dateObjectif;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Integer getTotalDuree() {
        return totalDuree;
    }

    public void setTotalDuree(Integer totalDuree) {
        this.totalDuree = totalDuree;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ActivitePhysique> getActivites() {
        return activites;
    }

    public void setActivites(List<ActivitePhysique> activites) {
        this.activites = activites;
    }


    @Override
    public String toString() {
        return "Objectif{" +
            "id=" + id +
            ", nomObjectif='" + nomObjectif + '\'' +
            ", dateObjectif=" + dateObjectif +
            ", totalCalories=" + totalCalories +
            ", totalDuree=" + totalDuree +
            ", note='" + note + '\'' +
            '}';
    }
}
