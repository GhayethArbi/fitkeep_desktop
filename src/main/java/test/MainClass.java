package test;

import models.User;
import services.ServiceCategory;
import utils.DBConnection;

import java.sql.SQLException;

public class MainClass {
    public static final String CURRENCY = "$";

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        User u = new User("Ben Daoued","Yosra", "yosra@gmail.com");
        ServiceCategory sc = new ServiceCategory();


        try {
            //sp.insertOne(u);

            System.out.println(sc.selectAll());

        } catch (SQLException e) {
            System.err.println("Erreur: "+e.getMessage());
        }


    }
}
