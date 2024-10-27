package com.agorohov.hibernatetestproject.util;

import com.agorohov.hibernatetestproject.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    // конфигурация через Java-код
    private static SessionFactory buildSessionFactory() {
        try {
            // Можно через Configuration, но этот метод новее и предпочтительнее
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            registryBuilder.applySetting("hibernate.connection.driver_class", "org.postgresql.Driver");
            registryBuilder.applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/hibernate_test_project");
            registryBuilder.applySetting("hibernate.connection.username", "agorohov");
            registryBuilder.applySetting("hibernate.connection.password", "agorohov");
            registryBuilder.applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            registryBuilder.applySetting("hibernate.show_sql", "true");
            registryBuilder.applySetting("hibernate.hbm2ddl.auto", "update");

            StandardServiceRegistry serviceRegistry = registryBuilder.build();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(User.class);

            Metadata metadata = metadataSources.getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // конфигурация через hibernate.properties
//    private static SessionFactory buildSessionFactory() {
//        try {
//            Properties properties = new Properties();
//            properties.load(HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties"));
//
//            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
//            registryBuilder.applySettings(properties);
//
//            StandardServiceRegistry standardRegistry = registryBuilder.build();
//
//            // Создаем MetadataSources
//            MetadataSources metadataSources = new MetadataSources(standardRegistry);
//            metadataSources.addAnnotatedClass(User.class); // Добавляем сущность
//
//            // Создаем Metadata
//            Metadata metadata = metadataSources.getMetadataBuilder().build();
//
//            // Создаем SessionFactory
//            return metadata.getSessionFactoryBuilder().build();
//        } catch (Throwable ex) {
//            System.err.println("Initial SessionFactory creation failed" + ex);
//            throw new ExceptionInInitializerError(ex);
//        }
//    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Закрываем пул соединений и освобождаем ресурсы
        getSessionFactory().close();
    }
}