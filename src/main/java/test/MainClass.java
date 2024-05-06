package test;

import models.Roles;
import models.User;
import models.User1;
import repository.UserRepository;
import services.ServiceCategory;

import services.ServiceUser;

import services.UserDao;
import services.session.UserSession;

import utils.DBConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class MainClass {
    public static final String CURRENCY = "$";

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();
        UserRepository ur = new UserRepository();
        //User u = new User(32,"arbi.ghayeth@gmail.com", Roles.ROLE_USER,"aaaaa","Ghayeth","Arbi","Male",new Date(2000,06,03),123456789,0,"jhvjh","Arina","hgjh",false,new Date(2024,4,16),"kjhkjn");

        User u = new User("Ben Daoued","Yosra", "yosra@gmail.com");
        ServiceCategory sc = new ServiceCategory();



        

        

        try {
            ServiceUser sp = new ServiceUser();
            //sp.insertOne(u);
            System.out.println(sp.selectAll());

            System.out.println(sc.selectAll());


            System.out.println(UserSession.CURRENT_USER);

            UserSession.CURRENT_USER.logout();
            System.out.println(UserSession.CURRENT_USER);
            ArrayList<User> users = new ArrayList<>();
            users.addAll(sp.selectAll());
            //users.stream().forEach(System.out::println);
            //System.out.println(sc.selectAll());*/
        } catch (SQLException e) {
            System.err.println("Erreur: "+e.getMessage());
        }


    }
}
