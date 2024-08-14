import entities.*;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory enf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = enf.createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Employee> employeeQuery = em.createQuery(
                "FROM Employee " +
                "WHERE firstName LIKE :pattern",
                Employee.class
        );
        employeeQuery.setParameter("pattern", scanner.nextLine() + "%");
        List<Employee> employees = employeeQuery.getResultList();

        for (Employee employee : employees) {
            System.out.format(
                "%s %s - %s - ($%.2f)\n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getJobTitle(),
                employee.getSalary()
            );
        }

        em.getTransaction().commit();
    }
}
