package models;

import java.util.Date;

public class Recette {
    private int id;
    private String name;
    private String category;
    private Date date;
    private String description;

    public Recette(int id) {
    }

    public Recette(String name, String category, Date date, String description) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public Recette(int id, String name, String category, Date date, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public Recette() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "recette{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                '}';
    }
}
