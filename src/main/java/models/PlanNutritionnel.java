package models;

import java.util.Date;

public class PlanNutritionnel {
    private int id;
    private Recette recettes;
    private String name;
    private Date date;

    public PlanNutritionnel() {
    }

    public PlanNutritionnel(int id, Recette recettes, String name, Date date) {
        this.id = id;
        this.recettes = recettes;
        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recette getRecettes() {
        return recettes;
    }

    public void setRecettes(Recette recettes) {
        this.recettes = recettes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PlanNutritionnel{" +
                "id=" + id +
                ", recettes=" + recettes +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }
}
