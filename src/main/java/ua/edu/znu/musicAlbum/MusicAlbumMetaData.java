package ua.edu.znu.musicAlbum;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MusicAlbumMetaData {

    public static void main(String[] args) {
        final String DB_CONNECTION = "jdbc:mysql://localhost:3306/music_album";
        final String DB_USER = "root";
        final String DB_PASSWORD = "77Ghfdj77";

        String strSQL = "SELECT * FROM album";

        try (Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            System.out.println("The " + connection.getCatalog() + " database is connected");

            /*Get database metadata*/
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            /*DBMS info*/
            int majorVersion = databaseMetaData.getDatabaseMajorVersion();
            int minorVersion = databaseMetaData.getDatabaseMinorVersion();
            String productName = databaseMetaData.getDatabaseProductName();
            String productVersion = databaseMetaData.getDatabaseProductVersion();
            System.out.println("We use DBMS: " + productName + "-" + productVersion + "-" + majorVersion + "." + minorVersion);

            /*Driver info*/
            int driverMajorVersion = databaseMetaData.getDriverMajorVersion();
            int driverMinorVersion = databaseMetaData.getDriverMinorVersion();
            String driverName = databaseMetaData.getDriverName();
            System.out.println("We use driver: " + driverName + " version " + driverMajorVersion + "." + driverMinorVersion);
            System.out.println("----------------------------------------------------");

            /*Get databases*/
            System.out.println("Databases list in DBMS:");
            ResultSet tables = databaseMetaData.getCatalogs();
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_CAT"));
            }
            System.out.println("----------------------------------------------------");

            /*Get database table list*/
            String catalog = "music_album";
            String schemaPattern = null;
            String tableNamePattern = null;
            String[] types = {"TABLE"};
            ResultSet result = databaseMetaData.getTables(
                    catalog, schemaPattern, tableNamePattern, types);
            System.out.println(catalog + " database tables:");
            while (result.next()) {
                System.out.println(result.getString(3));
            }
            System.out.println("----------------------------------------------------");

            tableNamePattern = "album";
            String columnNamePattern = null;
            result = databaseMetaData.getColumns(
                    catalog, schemaPattern, tableNamePattern, columnNamePattern);
            System.out.println("list of column names and types of the table " + tableNamePattern + ":");
            while (result.next()) {
                System.out.println(result.getString(4) + ":"
                        + result.getString(6));
            }
            System.out.println("----------------------------------------------------");

            /*ResultSet MetaData*/
            ResultSet rs = statement.executeQuery(strSQL);
            ResultSetMetaData rmd = rs.getMetaData();
            System.out.printf("Table %s data:%n", rmd.getTableName(1));
            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                System.out.printf("%-20s", rmd.getColumnName(i));
            }
            System.out.println();
            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                System.out.printf("%-20s", rmd.getColumnClassName(i));
            }
            System.out.println();
            for (int i = 1; i <= rmd.getColumnCount(); i++) {
                System.out.printf("%-20s", rmd.getColumnTypeName(i));
            }
            System.out.println();
            while (rs.next()) {
                int albumId = rs.getInt("album_id");
                String albumName = rs.getString("album_name");
                int releaseYear = rs.getInt("release_year");
                int genreId = rs.getInt("genre_id");
                System.out.printf("%-20d%-20s%-20d%-20d%n", albumId, albumName, releaseYear, genreId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
