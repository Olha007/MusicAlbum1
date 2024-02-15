package ua.edu.znu.musicAlbum.hibernateapp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import ua.edu.znu.musicAlbum.hibernateapp.entities.*;
import ua.edu.znu.musicAlbum.utils.HibernateJPAUtils;

import java.util.List;

public class MainHibernate {
    public static void main(String[] args) {
        try (Session session = HibernateJPAUtils.getSessionFactory().openSession()) {
            Album album1 = new Album();
            album1.setAlbumName("Album 1");
            album1.setReleaseYear(2022L);

            Album album2 = new Album();
            album2.setAlbumName("Album 2");
            album2.setReleaseYear(2023L);

            Artist artist1 = new Artist();
            artist1.setFirstName("John");
            artist1.setLastName("Doe");

            Artist artist2 = new Artist();
            artist2.setFirstName("Jane");
            artist2.setLastName("Smith");

            Genre genre1 = new Genre();
            genre1.setName("Rock");

            Genre genre2 = new Genre();
            genre2.setName("Pop");

            Group group1 = new Group();
            group1.setGroupName("Band 1");

            Group group2 = new Group();
            group2.setGroupName("Band 2");

            Song song1 = new Song();
            song1.setSongName("Song 1");
            song1.setDurationMinutes(3L);
            song1.setDurationSeconds(30L);

            Song song2 = new Song();
            song2.setSongName("Song 2");
            song2.setDurationSeconds(4L);
            song2.setDurationSeconds(15L);

            session.beginTransaction();
            session.save(album1);
            session.save(album2);
            session.save(artist1);
            session.save(artist2);
            session.save(genre1);
            session.save(genre2);
            session.save(group1);
            session.save(group2);
            session.save(song1);
            session.save(song2);
            session.getTransaction().commit();

            session.beginTransaction();
            artist1.setFirstName("Johnny");
            session.update(artist1);
            session.getTransaction().commit();

            List<Artist> artists = HibernateJPAUtils.getAllInstances(Artist.class, session);
            artists.forEach(System.out::println);

            session.beginTransaction();
            session.delete(group1);
            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {
            System.out.println("Duplicate insert!!! " + e.getMessage());
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}

