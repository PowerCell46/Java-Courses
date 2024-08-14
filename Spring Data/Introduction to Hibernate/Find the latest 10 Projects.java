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

        TypedQuery<Project> query = em.createQuery(
            "FROM " +
            "   Project " +
            "ORDER BY " +
            "   startDate DESC, " +
            "   name " +
            "", Project.class
        );
        query.setMaxResults(10);
        List<Project> last10Projects = query.getResultList();

        for (Project project : last10Projects) {
            System.out.format(
                "Project name: %s\n   Project description: %s\n   Project Start Date: %s\n    Project End Date: %s\n",
                project.getName(),
                project.getDescription(),
                project.getStartDate(),
                project.getEndDate());
        }
        
        em.getTransaction().commit();
    }
}
