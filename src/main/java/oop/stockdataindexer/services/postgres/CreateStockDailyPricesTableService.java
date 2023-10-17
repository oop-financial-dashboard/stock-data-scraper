package oop.stockdataindexer.services.postgres;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class CreateStockDailyPricesTableService {

//    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS stock_daily_price" +
//            "(symbol VARCHAR(50) NOT NULL ," +
//            " open VARCHAR(50), " +
//            " high VARCHAR(50), " +
//            " low VARCHAR(50), " +
//            " close VARCHAR(50), " +
//            " volume VARCHAR(50)," +
//            " timestamp TIMESTAMP NOT NULL," +
//            "PRIMARY KEY (symbol, timestamp));";

//    public static void main(String[] argv) throws SQLException {
//        CreateStockDailyPricesTableService createTableExample = new CreateStockDailyPricesTableService();
//        createTableExample.createTable();
//    }

//    public void createTable() throws SQLException {
//
//        System.out.println(createTableSQL);
//        // Step 1: Establishing a Connection
//        try (Connection connection = DriverManager.getConnection(url, user, password);
//
//             // Step 2:Create a statement using connection object
//             Statement statement = connection.createStatement();) {
//
//            // Step 3: Execute the query or update query
//            statement.execute(createTableSQL);
//            System.out.println("Created stock_daily_price table!");
//        } catch (SQLException e) {
//
//            // print SQL exception information
//            printSQLException(e);
//        }
//    }

//    public static void printSQLException(SQLException ex) {
//        InsertStockDailyPriceService.SQLException(ex);
//    }
}
