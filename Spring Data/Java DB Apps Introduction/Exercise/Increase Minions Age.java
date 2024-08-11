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

        int[] ids = Arrays.stream(scanner.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
        
        // SQL query
        StringBuilder sql = new StringBuilder(
            "UPDATE minions " +
            "SET " +
            "   age = age + 1, " +
            "   name = LOWER(name) " +
            "WHERE id IN (");

        for (int i = 0; i < ids.length; i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("?");
        }
        sql.append(")");
    
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());

        for (int i = 0; i < ids.length; i++) {
            preparedStatement.setInt(i + 1, ids[i]);
        }

        int resultUpdate = preparedStatement.executeUpdate();

//        System.out.println(resultUpdate);

        sql = new StringBuilder("SELECT * FROM minions");

        ResultSet resultSet = preparedStatement.executeQuery(sql.toString());

        while (resultSet.next()) {
            System.out.format(
                "%s %d\n",
                resultSet.getString("name"),
                resultSet.getInt("age")
            );
        }
    }
}
