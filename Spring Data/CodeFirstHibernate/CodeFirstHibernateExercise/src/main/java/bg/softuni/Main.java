package bg.softuni;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("code_first_hibernate_exercise");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();


        em.getTransaction().commit();
        em.close();
    }
}