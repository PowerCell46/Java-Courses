import entities.Address;
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

        String townName = "Sofia";
        TypedQuery<Town> sofiaTownQuery = em.createQuery("" +
            "FROM " +
            "   Town " +
            "WHERE " +
            "   name = :townName" +
            "", Town.class
        );
        sofiaTownQuery.setParameter("townName", townName);
        Town sofiaTownEntity = sofiaTownQuery.getSingleResult();

        Address vitoshkaAddr = new Address();
        vitoshkaAddr.setTown(sofiaTownEntity);
        vitoshkaAddr.setText("Vitoshka 15");
        em.persist(vitoshkaAddr);

        String lastName = scanner.nextLine();

        try {
            TypedQuery<Employee> employeeQuery = em.createQuery("" +
                    "FROM " +
                    "   Employee " +
                    "WHERE " +
                    "   lastName = :lastName" +
                    "", Employee.class);
            employeeQuery.setParameter("lastName", lastName);
            Employee employee = employeeQuery.getSingleResult();

            employee.setAddress(vitoshkaAddr);
            em.persist(employee);
            System.out.println("Successful operation!");

        } catch (NoResultException e) {
            System.out.println("No such employee!");
        }

        em.getTransaction().commit();
    }
}
