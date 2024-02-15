package ua.edu.znu.musicAlbum.hibernateapp;

import ua.edu.znu.musicAlbum.hibernateapp.entities.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class MainNamedQuery {
    public static void main(String[] args) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = Persistence.createEntityManagerFactory("musicAlbum").createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            //Додаємо до бази даних по 2 екземпляри пов’язаних сутностей
            Album album1 = new Album();
            album1.setAlbumName("Album 1");
            album1.setReleaseYear(2022L);
            entityManager.persist(album1);

            Album album2 = new Album();
            album2.setAlbumName("Album 2");
            album2.setReleaseYear(2023L);
            entityManager.persist(album2);

            transaction.commit();

            //Використовуємо поіменований запит для читання об'єктів за критерієм
            TypedQuery<Album> namedQuery = entityManager.createNamedQuery("Album.findByNameOrderByReleaseYearAsc", Album.class);
            namedQuery.setParameter("name", "Album 1");
            List<Album> albumsByName = namedQuery.getResultList();
            System.out.println("Albums with name 'Album 1':");
            albumsByName.forEach(System.out::println);

            //Використовуємо запит для оновлення властивості об'єктів за критерієм
            TypedQuery<Void> updateQuery = (TypedQuery<Void>) entityManager.createQuery("UPDATE Album a SET a.releaseYear = :releaseYear WHERE a.albumName = :name");
            updateQuery.setParameter("releaseYear", 2000L);
            updateQuery.setParameter("name", "Album 1");
            transaction.begin();
            updateQuery.executeUpdate();
            transaction.commit();

            //Використовуємо запит для видалення об'єктів за критерієм
            TypedQuery<Void> deleteQuery = (TypedQuery<Void>) entityManager.createQuery("DELETE FROM Album a WHERE a.albumName = :name");
            deleteQuery.setParameter("name", "Album 2");
            transaction.begin();
            deleteQuery.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}



