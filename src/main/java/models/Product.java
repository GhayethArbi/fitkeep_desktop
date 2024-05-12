package models;

public class Product {
    int id;
    Category category;
    String name;
    String slug;
    String illustration;
    String subtitle;
    String description;
    int quantite;
    int price;

    public Product() {
    }

    public Product(int id, Category category, String name, String slug, String illustration, String subtitle, String description, int quantite, int price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.slug = slug;
        this.illustration = illustration;
        this.subtitle = subtitle;
        this.description = description;
        this.quantite = quantite;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
}
