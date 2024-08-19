package bg.softuni;

import bg.softuni.entities.Bike;
import bg.softuni.entities.Car;
import bg.softuni.entities.Plane;
import bg.softuni.entities.Truck;
import bg.softuni.entities.relations.Company;
import bg.softuni.entities.relations.Driver;
import bg.softuni.entities.relations.PlateNumber;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("code_first_hibernate");

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

//        PlateNumber plateNumber = new PlateNumber("СВ3244ЕС");
//        em.persist(plateNumber);
//
//        Company company = new Company("Airlines");
//        em.persist(company);
//
//        Driver driver1 = new Driver("Gosho Kamiona");
//        em.persist(driver1);
//        Driver driver2 = new Driver("Mag Papazov");
//        em.persist(driver2);
//
//        Car car = new Car("X5", BigDecimal.valueOf(10_000), "Diesel", 4, plateNumber);
//        Bike bike = new Bike("T Max", BigDecimal.valueOf(7_000), "Diesel");
//        Plane plane = new Plane("Boeing 747", BigDecimal.valueOf(1_000_000), "Gas", 30, company);
//        em.persist(car);
//        em.persist(bike);
//        em.persist(plane);
//        em.persist(truck);

        Driver driver = em.find(Driver.class, 1L);
        em.persist(driver);

        Truck truck = new Truck("MAN", BigDecimal.valueOf(72_000), "Diesel", 6, List.of(driver));
        em.persist(truck);

        em.getTransaction().commit();
        em.close();

        System.out.println("works!");
    }
}