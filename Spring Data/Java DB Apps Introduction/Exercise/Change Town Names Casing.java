package bg.softuni;

import java.sql.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "PowerCell46");

        // Connection to the Database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        String countryName = scanner.nextLine();

        // SQL query
        String sql = "" +
                "UPDATE " +
                "   towns " +
                "SET " +
                "    name = UPPER(name) " +
                "WHERE " +
                "    country = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, countryName);

        int resultSet = preparedStatement.executeUpdate();

        if (resultSet == 0) {
            System.out.println("No town names were affected.");
        } else {
            sql = "SELECT * FROM towns WHERE country = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, countryName);

            ResultSet rs = preparedStatement.executeQuery();

            System.out.format("%d town names were affected.\n", resultSet);
            List<String> townNames = new ArrayList<>();

            while (rs.next()) {
                townNames.add(rs.getString("name"));
            }
            System.out.format("[%s]", String.join(", ", townNames));
        }
    }
}
