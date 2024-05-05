package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Objectif {
    private int id;
    private String nomObjectif;
    private Date dateObjectif;
    private int totalCalories;
    private int totalDuree;
    private String note;
<<<<<<< HEAD
    //private List<ActivitePhysique> activites;

    /*public Objectif() {
        this.activites=new ArrayList<>();
    }
     */
=======
    private List<ActivitePhysique> activites;

    public Objectif() {
        this.activites=new ArrayList<>();
    }
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275

    public Objectif(int id, String nomObjectif, Date dateObjectif, int totalCalories, int totalDuree, String note) {
        this.id = id;
        this.nomObjectif = nomObjectif;
        this.dateObjectif = dateObjectif;
        this.totalCalories = totalCalories;
        this.totalDuree = totalDuree;
        this.note = note;
<<<<<<< HEAD
     //   this.activites=new ArrayList<>();
=======
        this.activites=new ArrayList<>();
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomObjectif() {
        return nomObjectif;
    }

    public void setNomObjectif(String nomObjectif) {
        this.nomObjectif = nomObjectif;
    }

    public Date getDateObjectif() {
        return dateObjectif;
    }

    public void setDateObjectif(Date dateObjectif) {
        this.dateObjectif = dateObjectif;
    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getTotalDuree() {
        return totalDuree;
    }

    public void setTotalDuree(int totalDuree) {
        this.totalDuree = totalDuree;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

<<<<<<< HEAD
    /*public List<ActivitePhysique> getActivites() {
=======
    public List<ActivitePhysique> getActivites() {
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
        return activites;
    }

    public void setActivites(List<ActivitePhysique> activites) {
        this.activites = activites;
    }

<<<<<<< HEAD
     */

=======
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
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
