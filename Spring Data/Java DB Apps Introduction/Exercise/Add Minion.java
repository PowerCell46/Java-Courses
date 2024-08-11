package bg.softuni;

import java.sql.*;
import java.util.Arrays;
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

        String[] minionData = scanner.nextLine().split(": ")[1].split(" ");
        String villainName = scanner.nextLine().split(": ")[1];

        // SQL query
        String sql = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   villains " +
                "WHERE " +
                "   name = ?" +
                "";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, villainName);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            PreparedStatement createVillain = connection.prepareStatement("INSERT INTO villains (name, evilness_factor) VALUES (?, ?)");
            createVillain.setString(1, villainName);
            createVillain.setString(2, "evil");
            int createResult = createVillain.executeUpdate();
            if (createResult == 1) System.out.format("Villain %s was added to the database.\n", villainName);
        }

        // SQL query
        sql = "" +
                "SELECT " +
                "   * " +
                "FROM " +
                "   towns " +
                "WHERE " +
                "   name = ?" +
                "";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, minionData[2]);
        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            PreparedStatement createTown = connection.prepareStatement("INSERT INTO towns (name) VALUES (?)");
            createTown.setString(1, minionData[2]);
            int createResult = createTown.executeUpdate();
            if (createResult == 1) System.out.format("Town %s was added to the database.\n", minionData[2]);
        }

        // SQL query
        PreparedStatement createMinion = connection.prepareStatement("INSERT INTO minions (name, age, town_id) VALUES (?, ?, (SELECT id FROM towns WHERE name = ?))");
        createMinion.setString(1, minionData[0]);
        createMinion.setInt(2, Integer.parseInt(minionData[1]));
        createMinion.setString(3, minionData[2]);
        
        int createResult = createMinion.executeUpdate();
        if (createResult == 1) {
            PreparedStatement createMinionVillainRel = connection.prepareStatement("INSERT INTO minions_villains (minion_id, villain_id) VALUES ((SELECT id from minions WHERE name = ?), (SELECT id from villains WHERE name = ?))");
            createMinionVillainRel.setString(1, minionData[0]);
            createMinionVillainRel.setString(2, villainName);
            createResult = createMinionVillainRel.executeUpdate();
            
            if (createResult == 1) {
                System.out.format("Successfully added %s to be minion of %s.\n", minionData[0], villainName);
            }
        }
    }
}
