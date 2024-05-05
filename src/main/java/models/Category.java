package models;

import java.util.ArrayList;
import java.util.List;

public class Category {
<<<<<<< HEAD
    private int id;
    private String name;
    private List<Product> products;

    public Category() {
        this.products = new ArrayList<>();
    }

    public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.products = new ArrayList<>();
    }

    // Getters and setters
=======
    int id;
    String name;
    private List<Product> products;

>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
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

<<<<<<< HEAD
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
=======
    public Category() {
        this.products= new ArrayList<>();
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
<<<<<<< HEAD
=======

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
        this.products= new ArrayList<>();

    }
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
}
