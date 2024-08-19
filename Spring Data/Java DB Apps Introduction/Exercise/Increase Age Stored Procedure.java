package bg.softuni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "PowerCell46");

        // Connection to the Database
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        // SQL query
        String sql = "CALL usp_get_older(?)";

        int minionId = Integer.parseInt(bufferedReader.readLine());

        CallableStatement callableStatement = connection.prepareCall(sql);
        callableStatement.setInt(1, minionId);

        // executing the SQL query
        int result = callableStatement.executeUpdate();

        if (result > 0) {
            System.out.println("Successful aging of minion with id: " + minionId);

        } else {
            System.out.println("No change was been made.");
        }
    }
}
