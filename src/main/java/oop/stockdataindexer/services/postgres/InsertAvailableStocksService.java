package oop.stockdataindexer.services.postgres;

import oop.stockdataindexer.models.AvailableStocksRow;
import oop.stockdataindexer.models.StockDailyPriceRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static oop.stockdataindexer.services.postgres.InsertStockDailyPriceService.printSQLException;

public class InsertAvailableStocksService {
    private final String url = "jdbc:postgresql://ep-tight-bar-26500515-pooler.ap-southeast-1.aws.neon.tech/neondb?prepareThreshold=0";
    private final String user = "sticker99";
    private final String password = "4WfK9pJnaOoD";
    private static final String INSERT_USERS_SQL = "INSERT INTO available_stocks" +
            "  (symbol, name, last_indexed_date) VALUES " +
            " (?, ?, ?);";
    private AvailableStocksRow availableStocksRow;
    public InsertAvailableStocksService(AvailableStocksRow availableStocksRow) {
        this.availableStocksRow = availableStocksRow;
    }
    public void insertRecord() throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, this.availableStocksRow.getSymbol());
            preparedStatement.setString(2, this.availableStocksRow.getName());
            java.sql.Date sqlDate = java.sql.Date.valueOf(this.availableStocksRow.getLastIndexedDate());
            preparedStatement.setDate(3, sqlDate);

            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            System.out.println(this.availableStocksRow.getSymbol());
            printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }
}
