package bg.softuni;

import java.sql.*;
import java.util.*;

import static java.lang.StringTemplate.STR;


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
                "   * " +
                "FROM " +
                "   minions";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<String> minionsNames = new ArrayList<>();

        while (resultSet.next()) {
            minionsNames.add(resultSet.getString("name"));
        }

        for (int i = 0; i < minionsNames.size() / 2; i++) {
            System.out.println(minionsNames.get(i));
            System.out.println(minionsNames.get(minionsNames.size() - 1 - i));
        }

        if (minionsNames.size() % 2 != 0) {
            System.out.println(minionsNames.get(minionsNames.size() / 2));
        }
    }
}
