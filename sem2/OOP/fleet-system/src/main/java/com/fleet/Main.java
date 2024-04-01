package com.fleet;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // Load Hibernate configuration from hibernate.cfg.xml
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

            SessionFactory sessionFactory = configuration.buildSessionFactory();
            System.out.println("Database connection established successfully.");

            sessionFactory.close();
    }
}
