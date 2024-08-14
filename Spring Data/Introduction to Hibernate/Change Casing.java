import entities.Employee;
import entities.Town;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory enf = Persistence.createEntityManagerFactory("soft_uni");

        EntityManager em = enf.createEntityManager();

        em.getTransaction().begin();


        TypedQuery<Town> townsWithNameBiggerThan5Chars = em.createQuery(
            "FROM " +
            "   Town " +
            "WHERE " +
            "   LENGTH(name) > 5 " +
            "", Town.class
        );

        List<Town> resultList = townsWithNameBiggerThan5Chars.getResultList();

        for (Town town : resultList) {
            town.setName(town.getName().toUpperCase());
            em.persist(town);
        }

        em.getTransaction().commit();
    }
}
