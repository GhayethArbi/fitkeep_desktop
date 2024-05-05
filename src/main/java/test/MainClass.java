package test;

import models.User;
import services.ServiceCategory;
<<<<<<< HEAD
=======
import services.ServiceUser;
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
import utils.DBConnection;

import java.sql.SQLException;

public class MainClass {
<<<<<<< HEAD
    public static final String CURRENCY = "$";
=======
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        User u = new User("Ben Daoued","Yosra", "yosra@gmail.com");
        ServiceCategory sc = new ServiceCategory();
<<<<<<< HEAD


        try {
            //sp.insertOne(u);

=======
        ServiceUser sp = new ServiceUser();

        try {
            //sp.insertOne(u);
            System.out.println(sp.selectAll());
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
            System.out.println(sc.selectAll());

        } catch (SQLException e) {
            System.err.println("Erreur: "+e.getMessage());
        }


    }
}
