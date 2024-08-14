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


        TypedQuery<Employee> firstNameSalariesBiggerThan50_000 = em.createQuery(
            "FROM " +
            "   Employee " +
            "WHERE " +
            "   salary > 50000 " +
            "", Employee.class
        );

        List<Employee> resultList = firstNameSalariesBiggerThan50_000.getResultList();

        for (Employee employee : resultList) {
            System.out.println(employee.getFirstName());
        }

        em.getTransaction().commit();
    }
}
