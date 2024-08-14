import entities.Address;
import entities.Department;
import entities.Employee;
import entities.Town;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory enf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = enf.createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Object[]> query = em.createQuery(
            "SELECT a.text, COUNT(e.id) AS employeeCount " +
            "FROM Employee e " +
            "JOIN e.address a " +
            "GROUP BY a.id " +
            "ORDER BY employeeCount DESC", Object[].class
        );

        query.setMaxResults(10);

        List<Object[]> resultList = query.getResultList(); // [ [AddressText, CountOfEmployees], ... ]

        for (Object[] objects : resultList) {
            System.out.format("%s - %s employees\n", objects[0], objects[1]);
        }

        em.getTransaction().commit();
    }
}
