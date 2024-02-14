package ua.edu.znu.musicAlbum.utils;

import javax.persistence.Persistence;

public class GenerateDDLSchema {
    public static void main(String[] args) {
        Persistence.generateSchema("musicAlbum", null);
        System.out.println("DDL scripts were generated");
    }
}

