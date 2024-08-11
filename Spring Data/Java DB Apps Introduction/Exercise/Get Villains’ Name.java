package bg.softuni;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "PowerCell46");

        // Connection to the Database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        // SQL query
        String sql = "" +
            "SELECT " +
            "   villains.name, " +
            "   COUNT(minion_id) AS minions_count " +
            "FROM villains " +
            "JOIN minions_villains ON " +
            "   villains.id = minions_villains.villain_id " +
            "JOIN minions ON " +
            "   minions_villains.minion_id = minions.id " +
            "GROUP BY " +
            "   (villains.name) " +
            "HAVING " +
            "   COUNT(minion_id) > 15 " +
            "ORDER BY " +
            "   COUNT(minion_id) DESC;" +
            "";

        // executing the SQL query
        ResultSet resultSet = connection.createStatement().executeQuery(sql);

        while (resultSet.next()) {
            System.out.format(
                    "%s %d\n",
                resultSet.getString("name"),
                resultSet.getInt("minions_count")
            );
        }
    }
}
