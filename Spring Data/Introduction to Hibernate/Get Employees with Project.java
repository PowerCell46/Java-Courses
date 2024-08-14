import entities.*;

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

        TypedQuery<Employee> query = em.createQuery(
            "FROM Employee WHERE id = :id", Employee.class
        );
        query.setParameter("id", Integer.parseInt(scanner.nextLine()));
        Employee employee = query.getSingleResult();

        System.out.format(
            "%s %s - %s\n",
            employee.getFirstName(),
            employee.getLastName(),
            employee.getJobTitle()
        );

        employee.getProjects()
                .stream()
                .sorted((x1, x2) -> x1.getName().compareTo(x2.getName()))
                .forEach(p -> System.out.println("   " + p.getName()));

        em.getTransaction().commit();
    }
}
