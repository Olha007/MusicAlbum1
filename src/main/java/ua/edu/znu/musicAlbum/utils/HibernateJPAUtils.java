package ua.edu.znu.musicAlbum.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import java.util.List;

public class HibernateJPAUtils {
    /**
     * Get Hibernate Session Factory.
     *
     * @return Hibernate Session Factory instance
     */
    public static SessionFactory getSessionFactory() {
        /*Load configuration data from src/main/resources/hibernate.cfg.xml
          and build services registry use it*/
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        /*Build session factory using services registry*/
        SessionFactory sessionFactory = new MetadataSources(registry)
                .buildMetadata()
                .buildSessionFactory();
        return sessionFactory;
    }

    /**
     * Get with Hibernate all instances from the database table mapped to entity.
     *
     * @param entity  entity class
     * @param session Hibernate session
     * @param <T>     entity type
     * @return list of the persisted entity instances
     */
    public static <T> List<T> getAllInstances(Class<T> entity, Session session) {
        Query query = session.createQuery("from " + entity.getSimpleName());
        List<T> allInstances = query.list();
        return allInstances;
    }

    /**
     * Get with JPA all instances from the database table mapped to entity.
     *
     * @param entity        entity class
     * @param entityManager JPA Entity Manager
     * @param <T>           entity type
     * @return list of the persisted entity instances
     */
    public static <T> List<T> getAllInstances(Class<T> entity, EntityManager entityManager) {
        javax.persistence.Query query = entityManager.createQuery("from " + entity.getSimpleName());
        List<T> allInstances = query.getResultList();
        return allInstances;
    }
}
