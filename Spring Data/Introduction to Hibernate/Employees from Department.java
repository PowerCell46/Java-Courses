import entities.Department;
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

        
        String departmentName = "Research and Development"; 
        TypedQuery<Department> researchAndDev = em.createQuery(
            "FROM " +
            "   Department " +
            "WHERE " +
            "   name = :departmentName" +
            "", Department.class
        );
        researchAndDev.setParameter("departmentName", departmentName);
        Department resultDep = researchAndDev.getSingleResult();
        

        TypedQuery<Employee> employeesInResearchAndDevelopment = em.createQuery(
                "FROM " +
                "   Employee " +
                "WHERE " +
                "    department = :department " +
                "ORDER BY " +
                "    salary, " +
                "    id" +
            "", Employee.class
        );
        employeesInResearchAndDevelopment.setParameter("department", resultDep);
        List<Employee> resultList = employeesInResearchAndDevelopment.getResultList();

        
        for (Employee employee : resultList) {
            System.out.format("%s %s from %s - $%f\n",
                employee.getFirstName(),
                employee.getLastName(),
                resultDep.getName(),
                employee.getSalary());
        }

        
        em.getTransaction().commit();
    }
}
