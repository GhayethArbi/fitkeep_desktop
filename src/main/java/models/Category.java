package models;

import java.util.ArrayList;
import java.util.List;

public class Category {
    int id;
    String name;
    private List<Product> products;

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

    public Category() {
        this.products= new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.products= new ArrayList<>();

    }
}
