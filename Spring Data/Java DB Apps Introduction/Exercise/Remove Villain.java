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
                "   name " +
                "FROM villains " +
                "WHERE " +
                "   id = ?;" +
                "";

        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, villainId);

        // executing the SQL query
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            // SQL query
            sql = "" +
                "DELETE " +
                "FROM " +
                "   minions_villains " +
                "WHERE " +
                "   villain_id = ?";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, villainId);

            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows > 0) {
                sql = "" +
                    "DELETE " +
                    "FROM " +
                    "   villains " +
                    "WHERE" +
                    "   id = ?";

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, villainId);

                int deletedVillain = preparedStatement.executeUpdate();
                if (deletedVillain > 0) {
                    System.out.println(
                        "Successful deletion of villain with id: " + villainId +
                        ".\n" + deletedRows + " minions were been released."
                    );

                } else {
                    System.out.println("The villain with id: " + villainId + " was not deleted successfully.");
                }

            } else {
                System.out.println("No minions were been released.");
            }

        } else {
            System.out.println("No such villain was found.");
        }
    }
}
