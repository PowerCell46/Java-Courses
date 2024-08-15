package bg.softuni.orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DatabaseContext<T> {

    void doCreate(Class<T> entityClass) throws SQLException;

    boolean delete(T entity) throws SQLException, IllegalAccessException;

    void doAlter(T entity) throws SQLException;

    // save to the database
    boolean persist(T entity) throws SQLException, IllegalAccessException;

    Iterable<T> find(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    Iterable<T> find(Class<T> table, String where, boolean limitOne) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    T findFirst(Class<T> table) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    T findFirst(Class<T> table, String where) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
