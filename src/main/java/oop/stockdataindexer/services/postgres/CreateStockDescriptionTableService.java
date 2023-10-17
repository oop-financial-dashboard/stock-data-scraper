package oop.stockdataindexer.services.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateStockDescriptionTableService {
//    private static final String createTableSQL = "CREATE TABLE IF NOT EXISTS stock_description " +
//            "(symbol VARCHAR(50) PRIMARY KEY ," +
//            " exchange VARCHAR(50), " +
//            " currency VARCHAR(50), " +
//            " asset_type VARCHAR(50), " +
//            " name VARCHAR(50), " +
//            " description VARCHAR(50)," +
//            " country VARCHAR(50)," +
//            " sector VARCHAR(50)," +
//            " industry VARCHAR(50)," +
//            " fiscal_year_end VARCHAR(50)," +
//            " latest_quarter VARCHAR(50)," +
//            " market_capitalization VARCHAR(50)," +
//            " ebitda VARCHAR(50)," +
//            " pe_ratio VARCHAR(50)," +
//            " peg_ratio VARCHAR(50)," +
//            " book_value VARCHAR(50)," +
//            " dividend_per_share VARCHAR(50)," +
//            " dividend_yield VARCHAR(50)," +
//            " eps VARCHAR(50)," +
//            " revenue_per_share_ttm VARCHAR(50)," +
//            " profit_margin VARCHAR(50)," +
//            " operating_margin_ttm VARCHAR(50)," +
//            " return_on_assets_ttm VARCHAR(50)," +
//            " return_on_equity_ttm VARCHAR(50)," +
//            " revenue_ttm VARCHAR(50)," +
//            " gross_profit_ttm VARCHAR(50)," +
//            " diluted_eps_ttm VARCHAR(50)," +
//            " quarterly_earnings_growth_yoy VARCHAR(50)," +
//            " quarterly_revenue_growth_yoy VARCHAR(50)," +
//            " analyst_target_price VARCHAR(50)," +
//            " trailing_pe VARCHAR(50)," +
//            " forward_pe VARCHAR(50)," +
//            " price_to_sales_ratio_ttm VARCHAR(50)," +
//            " price_to_book_ratio VARCHAR(50)," +
//            " ev_to_revenue VARCHAR(50)," +
//            " ev_to_ebitda VARCHAR(50)," +
//            " beta VARCHAR(50)," +
//            " high_52_week VARCHAR(50)," +
//            " low_52_week VARCHAR(50)," +
//            " moving_average_50_day VARCHAR(50)," +
//            " moving_average_200_day VARCHAR(50)," +
//            " shares_outstanding VARCHAR(50)," +
//            " dividend_date VARCHAR(50)," +
//            " ex_dividend_date VARCHAR(50))";
////    public static void main(String[] argv) throws SQLException {
////        CreateStockDescriptionTableService createTableExample = new CreateStockDescriptionTableService();
////        createTableExample.createTable();
////    }
//
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
//            System.out.println("Created stock_daily_prices table!");
//        } catch (SQLException e) {
//
//            // print SQL exception information
//            printSQLException(e);
//        }
//    }
//
//    public static void printSQLException(SQLException ex) {
//        InsertStockDailyPriceService.SQLException(ex);
//    }
}
