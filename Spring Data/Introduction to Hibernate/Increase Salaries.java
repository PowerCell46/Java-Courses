import entities.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        EntityManagerFactory enf = Persistence.createEntityManagerFactory("soft_uni");
        EntityManager em = enf.createEntityManager();

        em.getTransaction().begin();

        TypedQuery<Department> departmentsQuery = em.createQuery(
            "FROM Department " +
            "WHERE " +
            "   name IN ('Engineering', 'Tool Design', 'Marketing', 'Information Services')" +
            "", Department.class
        );
        List<Department> departments = departmentsQuery.getResultList();


        TypedQuery<Employee> employeesQuery = em.createQuery(
                "FROM Employee " +
                "WHERE department IN (:departments) " +
                "", Employee.class
        );
        employeesQuery.setParameter("departments", departments);
        List<Employee> employees = employeesQuery.getResultList();

        for (Employee employee : employees) {
            employee.setSalary(BigDecimal.valueOf(employee.getSalary().doubleValue() * 1.12));
            em.persist(employee);

            System.out.format(
                "%s %s ($%.2f)\n",
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary()
            );
        }

        em.getTransaction().commit();
    }
}
