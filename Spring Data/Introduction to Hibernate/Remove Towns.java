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

        String townName = scanner.nextLine();

        TypedQuery<Town> townTypedQuery = em.createQuery(
                "FROM Town " +
                "WHERE name = :name" +
                "", Town.class
        );
        townTypedQuery.setParameter("name", townName);

        try {
            Town town = townTypedQuery.getSingleResult();

            TypedQuery<Address> addressTypedQuery = em.createQuery(
                    "FROM Address " +
                    "WHERE town = :town" +
                    "", Address.class
            );
            addressTypedQuery.setParameter("town", town);

            List<Address> addresses = addressTypedQuery.getResultList();

            int numberOfAddresses = 0;

            for (Address address : addresses) {

                TypedQuery<Employee> employeeTypedQuery = em.createQuery(
                        "FROM Employee " +
                        "WHERE address = :address" +
                        "", Employee.class
                );
                employeeTypedQuery.setParameter("address", address);
                List<Employee> employees = employeeTypedQuery.getResultList();

                for (Employee employee : employees) {
                    employee.setAddress(null);
                    em.persist(employee);
                }

                em.remove(address);
                numberOfAddresses++;
            }

            em.remove(town);

            if (numberOfAddresses == 1) {
                System.out.format("1 address in %s deleted", townName);

            } else {
                System.out.format("%d addresses in %s deleted", numberOfAddresses, townName);
            }

        } catch(NoResultException e) {
            System.out.println("No such Town exists!");
        }

        em.getTransaction().commit();
    }
}
