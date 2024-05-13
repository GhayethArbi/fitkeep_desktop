package Entity;

import models.User;

import java.sql.Timestamp;

public class Commande {
    private int id;
    private Panier panier;
    private User user;
    private String modeDePaiement;
    private Timestamp date;
    private String adresse;
    private String statut;

    // Constructors
    public Commande() {
    }

    public Commande(int idCommande, Panier panier, User user, String modeDePaiement, Timestamp date, String adresse, String statut) {
        this.id = idCommande;
        this.panier = panier;
        this.user = user;
        this.modeDePaiement = modeDePaiement;
        this.date = date;
        this.adresse = adresse;
        this.statut = statut;
    }

    // Getters and Setters
    public int getIdCommande() {
        return id;
    }

    public void setIdCommande(int idCommande) {
        this.id = idCommande;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getModeDePaiement() {
        return modeDePaiement;
    }

    public void setModeDePaiement(String modeDePaiement) {
        this.modeDePaiement = modeDePaiement;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    // toString method
    @Override
    public String toString() {
        return "Commande{" +
                "idCommande=" + id +
                ", panier=" + panier +
                ", user=" + user +
                ", modeDePaiement='" + modeDePaiement + '\'' +
                ", date=" + date +
                ", adresse='" + adresse + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }

    public void setSelectPanier(boolean b) {
    }
}
