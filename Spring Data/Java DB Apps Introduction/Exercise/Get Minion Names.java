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
            "    villains.name, " +
            "    minions.name, " +
            "    minions.age " +
            "    FROM villains " +
            "JOIN minions_villains ON villains.id = minions_villains.villain_id " +
            "JOIN minions ON minions_villains.minion_id = minions.id " +
            "WHERE villain_id = ?;" +
            "";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int villainId = Integer.parseInt(scanner.nextLine());
        preparedStatement.setInt(1, villainId);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.format("No villain with ID %d exists in the database.", villainId);
            return;
        }
        int index = 0;
        System.out.format("Villain: %s\n", resultSet.getString("villains.name"));

//         iterating through the results
        while (resultSet.next()) {
            index++;
            System.out.format(
                    "%d. %s %d\n",
                index,
                resultSet.getString("minions.name"),
                resultSet.getInt("minions.age")
            );
        }
    }
}
