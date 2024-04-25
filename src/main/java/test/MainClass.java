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

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();
        UserRepository ur = new UserRepository();
        //User u = new User(32,"arbi.ghayeth@gmail.com", Roles.ROLE_USER,"aaaaa","Ghayeth","Arbi","Male",new Date(2000,06,03),123456789,0,"jhvjh","Arina","hgjh",false,new Date(2024,4,16),"kjhkjn");

        /*User1 u1= new User1.UserBuilder()
                .setId(33)
                .setAddress("Arina")
                .setEmail("kjnkbj@gmail.com")
                .setBirthDay(null)
                .setName("Mootaz")
                .setLastName("Choufen")
                .setPassword("jknjkn")
                .setGender("Male")
                .build();
        System.out.println(u1.getEmail()+ u1.getName());*/
        //ServiceCategory sc = new ServiceCategory();
       // ServiceUser sp = new ServiceUser();
        //UserDao ud = new UserDao();
        try {
            System.out.println(ur.findById(42));
          /*  String userSession=ud.login("test222@gmail.com","aaaaa");
            System.out.println(userSession);
           // sp.insertOne1(u1);
           //sp.insertOne(u);

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
