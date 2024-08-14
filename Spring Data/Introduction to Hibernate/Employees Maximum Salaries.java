import entities.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory enf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = enf.createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Department> departmentTypedQuery = em.createQuery("FROM Department ", Department.class);
        List<Department> departments = departmentTypedQuery.getResultList();

        for (Department department : departments) {
            BigDecimal maxSalary = department.getEmployees()
                .stream()
                .map(e -> e.getSalary())
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.valueOf(0));
                
            if (maxSalary.compareTo(BigDecimal.valueOf(30_000)) < 0 || maxSalary.compareTo(BigDecimal.valueOf(70_000)) > 0) {
                System.out.format(
                    "%s %.2f\n",
                    department.getName(),
                    maxSalary
                );
            }
        }

        em.getTransaction().commit();
    }
}
