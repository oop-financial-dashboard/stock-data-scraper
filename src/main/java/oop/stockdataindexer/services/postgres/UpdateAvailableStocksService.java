package oop.stockdataindexer.services.postgres;

import oop.stockdataindexer.models.AvailableStocksRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static oop.stockdataindexer.services.postgres.InsertStockDailyPriceService.printSQLException;

public class UpdateAvailableStocksService {
    private final String url = "jdbc:postgresql://ep-tight-bar-26500515-pooler.ap-southeast-1.aws.neon.tech/neondb?prepareThreshold=0";
    private final String user = "sticker99";
    private final String password = "4WfK9pJnaOoD";
    private static final String UPDATE_USERS_SQL = "UPDATE available_stocks SET last_indexed_date = ? WHERE symbol = ?;";
    private AvailableStocksRow availableStocksRow;

    public UpdateAvailableStocksService(AvailableStocksRow availableStocksRow) {
        this.availableStocksRow = availableStocksRow;
    }

    public void updateLastIndexedDate() throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            java.sql.Date sqlDate = java.sql.Date.valueOf(this.availableStocksRow.getLastIndexedDate());
            preparedStatement.setDate(1, sqlDate);
            preparedStatement.setString(2, this.availableStocksRow.getSymbol());
            System.out.println("HELLO I TRIED TO EXECUTE UPDATE");
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            System.out.println(this.availableStocksRow.getSymbol());
            printSQLException(e);
        }
    }
}
