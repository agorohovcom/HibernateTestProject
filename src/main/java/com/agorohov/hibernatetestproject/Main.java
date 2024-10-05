package com.agorohov.hibernatetestproject;

import com.agorohov.hibernatetestproject.model.User;
import com.agorohov.hibernatetestproject.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            User user = new User("Sasha");

            session.merge(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(System.err);
        } finally {
            session.close();
            HibernateUtil.shutdown();
        }
    }
}
