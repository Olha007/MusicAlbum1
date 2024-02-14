package ua.edu.znu.musicAlbum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerDatabaseConnect {
    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/music_album";
        final String DB_USER = "root";
        final String DB_PASS = "77Ghfdj77";

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASS)) {
            System.out.println("The " + connection.getCatalog() + " was connected.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
