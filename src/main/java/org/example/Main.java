package org.example;

import Controllers.MainFx;
import Database.MyConnections;
import Entity.Panier;
import Entity.User;
import Services.PanierService;
import Services.UserService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        MainFx.main(args);
    }
}
