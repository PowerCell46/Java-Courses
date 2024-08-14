import entities.Employee;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManagerFactory enf = Persistence.createEntityManagerFactory("soft_uni");

        EntityManager em = enf.createEntityManager();

        em.getTransaction().begin();

        String firstName = scanner.nextLine();
        String lastName = scanner.nextLine();

        TypedQuery<Employee> fullNameEmployeeQuery = em.createQuery(
            "FROM " +
            "   Employee " +
            "WHERE " +
            "   firstName = :firstName AND " +
            "   lastName = :lastName " +
            "", Employee.class
        );

        fullNameEmployeeQuery.setParameter("firstName", firstName);
        fullNameEmployeeQuery.setParameter("lastName", lastName);


        try {
            Employee employee = fullNameEmployeeQuery.getSingleResult();
            System.out.println(employee);

        } catch (NoResultException e) {
            System.out.println("No");
        }
        
        em.getTransaction().commit();
    }
}
