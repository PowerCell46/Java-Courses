package bg.softuni.orm;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


@NoArgsConstructor
public class MyConnector {

    @Getter
    private static Connection connection;

    public static void createConnection(String username, String password, String dbName) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        connection = DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", dbName), properties);
    }
}
