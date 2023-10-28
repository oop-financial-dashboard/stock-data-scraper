package oop.stockdataindexer.services.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static oop.stockdataindexer.services.postgres.InsertStockDailyPriceService.printSQLException;

public class GetLastIndexedDate {
    private final String url = "jdbc:postgresql://ep-autumn-mud-09899474-pooler.us-east-2.aws.neon.tech/neondb";
    private final String user = "sticker99";
    private final String password = "4WfK9pJnaOoD";

    private static final String SELECT_LATEST_DATE_SQL = "SELECT MAX(last_indexed_date) AS latest_date FROM available_stocks;";

    public void selectRecord() throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

//             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_DATE_SQL)) {
//            preparedStatement.setString(1, this.stockDailyPriceRow.getSymbol());
//            preparedStatement.setString(2, this.stockDailyPriceRow.getOpen());
//            preparedStatement.setString(3, this.stockDailyPriceRow.getHigh());
//            preparedStatement.setString(4, this.stockDailyPriceRow.getLow());
//            preparedStatement.setString(5, this.stockDailyPriceRow.getClose());
//            preparedStatement.setString(6, this.stockDailyPriceRow.getVolume());
//            java.sql.Date sqlDate = java.sql.Date.valueOf(this.stockDailyPriceRow.getTimestamp());
//            preparedStatement.setDate(7, sqlDate);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeQuery();
        } catch (SQLException e) {

            // print SQL exception information
            System.out.println("Failed to retrieve last indexed date");
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
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
