package models;

public class Product {
    private int id;
    private int category_id; // New property for category_id
    private Category category; // Reference to the Category
    private String name;
    private String slug;
    private String illustration;
    private String subtitle;
    private String description;
    private int quantite;
    private int price;
    private String color;

    public Product() {
    }

    public Product(int id, Category category, String name, String slug, String illustration, String subtitle, String description, int quantite, int price) {
        this.id = id;
        this.category_id = category.getId(); // Set category_id based on Category's id
        this.category = category;
        this.name = name;
        this.slug = slug;
        this.illustration = illustration;
        this.subtitle = subtitle;
        this.description = description;
        this.quantite = quantite;
        this.price = price;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.category_id = category.getId(); // Update category_id when category is set
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getCategoryName() {
        if (category != null) {
            return category.getName();
        } else {
            return null;
        }
    }

}
