package oop.stockdataindexer.services.postgres;

import java.sql.*;
import java.time.LocalDate;

import static oop.stockdataindexer.services.postgres.InsertStockDailyPriceService.printSQLException;

public class GetLastIndexedDate {
    private final String url = "jdbc:postgresql://ep-tight-bar-26500515-pooler.ap-southeast-1.aws.neon.tech/neondb?prepareThreshold=0";
    private final String user = "sticker99";
    private final String password = "4WfK9pJnaOoD";
    private static final String SELECT_LATEST_DATE_SQL = "SELECT MAX(last_indexed_date) AS latest_date FROM available_stocks;";

    public LocalDate selectRecord() {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

//             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_LATEST_DATE_SQL)) {

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getDate(1).toLocalDate();
            }

        } catch (SQLException e) {

            // print SQL exception information
            System.out.println("Failed to retrieve last indexed date");
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.

        return null;
    }
}
