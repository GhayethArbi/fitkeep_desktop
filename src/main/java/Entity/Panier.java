package Entity;

import models.User;
import models.Product;
public class Panier {
    private int id;
    private User user;
    private Product product;
    private int quantite;
    private double totalPrice;

    // Constructors
    public Panier() {
    }

    public Panier(int idPanier, User user, Product product, int quantite, double totalPrice) {
        this.id = idPanier;
        this.user = user;
        this.product = product;
        this.quantite = quantite;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getIdPanier() {
        return id;
    }

    public void setIdPanier(int idPanier) {
        this.id = idPanier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // toString method
    @Override
    public String toString() {
        return "Panier{" +
                "idPanier=" + id +
                ", user=" + user +
                ", product=" + product +
                ", quantite=" + quantite +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
