package ua.edu.znu.musicAlbum.hibernateapp;

import ua.edu.znu.musicAlbum.hibernateapp.entities.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class MainJPA {
    private static EntityManagerFactory entityManagerFactory = null;
    private static EntityManager entityManager = null;

    public static void main(String[] args) {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("musicAlbum");
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();

            // Створення та збереження першого об'єкта
            Album album1 = new Album();
            album1.setAlbumName("Album 1");
            album1.setReleaseYear(2000L);
            entityManager.persist(album1);

            // Створення та збереження другого об'єкта
            Album album2 = new Album();
            album2.setAlbumName("Album 2");
            album2.setReleaseYear(2002L);
            entityManager.persist(album2);

            entityManager.getTransaction().commit();

            //Читання об'єктів за основним атрибутом
            Long albumId = 1L;
            Album foundAlbum = entityManager.find(Album.class, albumId);
            System.out.println("Знайдений альбом за основним атрибутом: " + foundAlbum);

            // Оновлення атрибутів об'єктів за умовою
            entityManager.getTransaction().begin();

            // Знаходимо альбом за умовою і оновлюємо його атрибут
            Album albumToUpdate = entityManager.createQuery("SELECT a FROM Album a WHERE a.albumName = :name", Album.class)
                    .setParameter("name", "Album 1")
                    .getSingleResult();
            albumToUpdate.setReleaseYear(1999L);
            entityManager.merge(albumToUpdate);

            entityManager.getTransaction().commit();

            //Видалення об'єктів за умовою
            entityManager.getTransaction().begin();

            // Знаходимо альбом за умовою і видаляємо його
            Album albumToDelete = entityManager.createQuery("SELECT a FROM Album a WHERE a.albumName = :name", Album.class)
                    .setParameter("name", "Album 2")
                    .getSingleResult();
            entityManager.remove(albumToDelete);

            entityManager.getTransaction().commit();

            //Виведення всіх альбомів
            List<Album> albums = entityManager.createQuery("SELECT a FROM Album a", Album.class)
                    .getResultList();
            System.out.println("Усі альбоми:");
            albums.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            if (entityManagerFactory != null) {
                entityManagerFactory.close();
            }
        }
    }
}



