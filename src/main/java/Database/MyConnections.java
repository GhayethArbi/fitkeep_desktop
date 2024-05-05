package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnections {

    private  String url="jdbc:mysql://localhost:3306/test1";
    private final String login="root";
    private final String pwd="";
    public  static MyConnections instance;

    Connection cnx;

    public MyConnections(){
        try {
            cnx = DriverManager.getConnection(url,login,pwd);
            System.out.println("Connexion établie!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }



    }
    public static MyConnections getInstance() {
        if (instance == null) {
            instance = new MyConnections();
        }
        return instance;
    }
    public Connection getCnx(){
        return cnx;

    }



}
