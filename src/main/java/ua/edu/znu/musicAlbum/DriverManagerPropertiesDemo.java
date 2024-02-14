package ua.edu.znu.musicAlbum;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DriverManagerPropertiesDemo {
    public static void main(String[] args) {
        Properties config = new Properties();
        try (FileInputStream fis = new FileInputStream("/Users/olhanozdriukhina/IdeaProjects/MusicAlbum/src/main/java/ua/edu/znu/musicAlbum/config.properties")) {
            config.load(fis);
            String connectionUrl = "jdbc:mysql://" + config.getProperty("host") + ":"
                    + config.getProperty("port") + "/" + config.getProperty("dbname");
            try (Connection connection = DriverManager.getConnection(connectionUrl, config)) {
                System.out.println("The " + connection.getCatalog() + " database is connected");
            }
        } catch (IOException | SQLException ex) {
            ex.printStackTrace();
        }
    }
}
