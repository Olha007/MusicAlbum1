package ua.edu.znu.musicAlbum.hibernateapp;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import ua.edu.znu.musicAlbum.hibernateapp.entities.*;
import ua.edu.znu.musicAlbum.utils.HibernateJPAUtils;

import javax.persistence.criteria.*;
import java.util.List;

public class MainHibernateQueries {

    public static void main(String[] args) {
        try (Session session = HibernateJPAUtils.getSessionFactory().openSession()) {
            //Додавання даних
            Artist artist1 = new Artist();
            artist1.setFirstName("John");
            artist1.setLastName("Doe");

            Artist artist2 = new Artist();
            artist2.setFirstName("Jane");
            artist2.setLastName("Smith");

            Group group1 = new Group();
            group1.setGroupName("Band 1");

            Group group2 = new Group();
            group2.setGroupName("Band 2");

            Album album1 = new Album();
            album1.setAlbumName("Album 1");
            album1.setReleaseYear(2022L);

            Album album2 = new Album();
            album2.setAlbumName("Album 2");
            album2.setReleaseYear(2023L);

            Genre genre1 = new Genre();
            genre1.setName("Rock");

            Genre genre2 = new Genre();
            genre2.setName("Pop");

            Song song1 = new Song();
            song1.setSongName("Song 1");
            song1.setDurationMinutes(3L);
            song1.setDurationSeconds(30L);

            Song song2 = new Song();
            song2.setSongName("Song 2");
            song2.setDurationMinutes(4L);
            song2.setDurationSeconds(15L);

            session.beginTransaction();
            session.save(artist1);
            session.save(artist2);
            session.save(group1);
            session.save(group2);
            session.save(album1);
            session.save(album2);
            session.save(genre1);
            session.save(genre2);
            session.save(song1);
            session.save(song2);
            session.getTransaction().commit();

            /* SELECT ALL */
            // Використання Native SQL
            NativeQuery sqlQuery = session.createSQLQuery("SELECT * FROM artist");
            sqlQuery.addEntity(Artist.class);
            List<Artist> allArtists = sqlQuery.list();
            System.out.println("Artists info (Native SQL):");
            allArtists.forEach(System.out::println);

            // Використання HQL
            Query hqlQuery = session.createQuery("from Album");
            List<Album> allAlbums = hqlQuery.list();
            System.out.println("Albums info (HQL):");
            allAlbums.forEach(System.out::println);

            // Використання Criteria API
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
            Root<Group> root = criteriaQuery.from(Group.class);
            criteriaQuery.select(root);
            Query<Group> allGroupsQuery = session.createQuery(criteriaQuery);
            List<Group> allGroups = allGroupsQuery.getResultList();
            System.out.println("Groups info (Criteria API):");
            allGroups.forEach(System.out::println);

            /* SELECT WHERE */
            // Використання Native SQL з параметрами
            String artistName = "John";
            String artistSurname = "Doe";
            sqlQuery = session.createSQLQuery("SELECT * FROM artist WHERE first_name = :firstName AND last_name = :lastName");
            sqlQuery.addEntity(Artist.class);
            sqlQuery.setParameter("firstName", artistName);
            sqlQuery.setParameter("lastName", artistSurname);
            Artist foundArtist = (Artist) sqlQuery.uniqueResult();
            System.out.println("Info for artist (Native SQL): " + artistName + " " + artistSurname + ":");
            System.out.println(foundArtist);

            // Використання HQL з параметрами
            int releaseYear = 2022;
            hqlQuery = session.createQuery("from Album a where a.releaseYear = :year")
                    .setParameter("year", releaseYear);
            Album foundAlbum = (Album) hqlQuery.uniqueResult();
            System.out.println("Search album with year " + releaseYear + " (HQL):");
            System.out.println(foundAlbum);

            // Використання Criteria API з параметрами
            String groupName = "Band 1";
            System.out.println("Search group with name " + groupName + " (Criteria API):");
            criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Group> criteriaQueryGroup = criteriaBuilder.createQuery(Group.class);
            Root<Group> rootGroup = criteriaQueryGroup.from(Group.class);
            criteriaQueryGroup.select(rootGroup).where(criteriaBuilder.equal(rootGroup.get("groupName"), groupName));
            Query<Group> singleGroupQuery = session.createQuery(criteriaQueryGroup);
            Group foundGroup = singleGroupQuery.uniqueResult();
            System.out.println(foundGroup);

            /* UPDATE WHERE */
            session.beginTransaction();
            // Використання Native SQL для оновлення
            String newName = "Johnathan";
            String oldName = "John";
            sqlQuery = session.createSQLQuery("UPDATE artist SET first_name = :newName WHERE first_name = :oldName");
            sqlQuery.setParameter("newName", newName);
            sqlQuery.setParameter("oldName", oldName);
            int updatedRows = sqlQuery.executeUpdate();
            System.out.println("Updated by Native SQL query " + updatedRows + " rows.");

            // Використання HQL для оновлення
            int newReleaseYear = 2024;
            int oldReleaseYear = 2023;
            hqlQuery = session.createQuery("update Album set releaseYear = :newYear where releaseYear = :oldYear");
            hqlQuery.setParameter("newYear", newReleaseYear);
            hqlQuery.setParameter("oldYear", oldReleaseYear);
            updatedRows = hqlQuery.executeUpdate();
            System.out.println("Updated by HQL query " + updatedRows + " rows.");

            // Використання Criteria API для оновлення
            int newGroupYear = 2025;
            int oldGroupYear = 2024;
            CriteriaUpdate<Group> updateCriteriaQuery = criteriaBuilder.createCriteriaUpdate(Group.class);
            Root<Group> groupRoot = updateCriteriaQuery.from(Group.class);
            updateCriteriaQuery.set("year", newGroupYear);
            updateCriteriaQuery.where(criteriaBuilder.equal(groupRoot.get("year"), oldGroupYear));
            Query<Group> updateGroupQuery = session.createQuery(updateCriteriaQuery);
            updatedRows = updateGroupQuery.executeUpdate();
            System.out.println("Updated by Criteria API query " + updatedRows + " rows.");
            session.getTransaction().commit();

            /* DELETE WHERE */
            session.beginTransaction();
            // Використання Native SQL для видалення
            int artistId = 1;
            sqlQuery = session.createSQLQuery("DELETE FROM artist WHERE artist_id = :artistId");
            sqlQuery.setParameter("artistId", artistId);
            int deletedRows = sqlQuery.executeUpdate();
            System.out.println("Deleted by Native SQL query " + deletedRows + " rows.");

            // Використання HQL для видалення
            int albumId = 1;
            hqlQuery = session.createQuery("delete from Album where id=:albumId");
            hqlQuery.setParameter("albumId", albumId);
            deletedRows = hqlQuery.executeUpdate();
            System.out.println("Deleted by HQL query " + deletedRows + " rows.");

            // Використання Criteria API для видалення
            groupName = "Band 2";
            CriteriaDelete<Group> deleteCriteriaQuery = criteriaBuilder.createCriteriaDelete(Group.class);
            Root<Group> groupRootDelete = deleteCriteriaQuery.from(Group.class);
            deleteCriteriaQuery.where(criteriaBuilder.equal(groupRootDelete.get("groupName"), groupName));
            Query<Group> deleteGroupQuery = session.createQuery(deleteCriteriaQuery);
            deletedRows = deleteGroupQuery.executeUpdate();
            System.out.println("Deleted by Criteria API query " + deletedRows + " rows.");

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

    }
}



