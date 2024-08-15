package bg.softuni;

import bg.softuni.entities.Order;
import bg.softuni.entities.User;
import bg.softuni.orm.EntityManager;
import bg.softuni.orm.MyConnector;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {

        MyConnector.createConnection("root", "PowerCell46", "mini_orm");

        Connection connection = MyConnector.getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);

        EntityManager<Order> orderEntityManager = new EntityManager<>(connection);

    }
}