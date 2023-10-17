package oop.stockdataindexer.services.postgres;
import oop.stockdataindexer.models.StockDailyPriceRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertStockDailyPriceService {
    private final String url = "jdbc:postgresql://ep-autumn-mud-09899474-pooler.us-east-2.aws.neon.tech/neondb";
    private final String user = "sticker99";
    private final String password = "CPQm9Apsjh6O";
    private static final String INSERT_USERS_SQL = "INSERT INTO stock_daily_price" +
            "  (symbol, open, high, low, close, volume, timestamp) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?);";
    private StockDailyPriceRow stockDailyPriceRow;

    public InsertStockDailyPriceService(StockDailyPriceRow stockDailyPriceRow) {
        this.stockDailyPriceRow = stockDailyPriceRow;
    }

    public void insertRecord() throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
                preparedStatement.setString(1, this.stockDailyPriceRow.getSymbol());
                preparedStatement.setString(2, this.stockDailyPriceRow.getOpen());
                preparedStatement.setString(3, this.stockDailyPriceRow.getHigh());
                preparedStatement.setString(4, this.stockDailyPriceRow.getLow());
                preparedStatement.setString(5, this.stockDailyPriceRow.getClose());
                preparedStatement.setString(6, this.stockDailyPriceRow.getVolume());
                java.sql.Date sqlDate = java.sql.Date.valueOf(this.stockDailyPriceRow.getTimestamp());
                preparedStatement.setDate(7, sqlDate);


            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            System.out.println(this.stockDailyPriceRow.getSymbol());
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }

    public static void printSQLException(SQLException ex) {
        SQLException(ex);
    }

    static void SQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
