package oop.stockdataindexer.services.postgres;

import oop.stockdataindexer.models.StockDescriptionRow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertStockDescriptionService {
    private final String url = "jdbc:postgresql://ep-autumn-mud-09899474-pooler.us-east-2.aws.neon.tech/neondb";
    private final String user = "sticker99";
    private final String password = "CPQm9Apsjh6O";
    private static final String INSERT_USERS_SQL = "INSERT INTO stock_description" +
            "  (symbol, exchange, currency, asset_type, name, description, country, sector, industry, fiscal_year_end, latest_quarter, market_capitalization, ebitda, pe_ratio, peg_ratio, book_value, dividend_per_share, dividend_yield, eps, revenue_per_share_ttm, profit_margin, operating_margin_ttm, return_on_assets_ttm, return_on_equity_ttm, revenue_ttm, gross_profit_ttm, diluted_eps_ttm, quarterly_earnings_growth_yoy, quarterly_revenue_growth_yoy, analyst_target_price, trailing_pe, forward_pe, price_to_sales_ratio_ttm, price_to_book_ratio, ev_to_revenue, ev_to_ebitda, beta, high_52_week, low_52_week, moving_average_50_day, moving_average_200_day, shares_outstanding, dividend_date, ex_dividend_date) VALUES " +
            " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private StockDescriptionRow stockDescriptionRow;
    public InsertStockDescriptionService(StockDescriptionRow stockDescriptionRow) {
        this.stockDescriptionRow = stockDescriptionRow;
    }

    public void insertRecord() throws SQLException {
        // Step 1: Establishing a Connection
        try (Connection connection = DriverManager.getConnection(url, user, password);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, this.stockDescriptionRow.getSymbol());
            preparedStatement.setString(2, this.stockDescriptionRow.getExchange());
            preparedStatement.setString(3, this.stockDescriptionRow.getCurrency());
            preparedStatement.setString(4, this.stockDescriptionRow.getAssetType());
            preparedStatement.setString(5, this.stockDescriptionRow.getName());
            preparedStatement.setString(6, this.stockDescriptionRow.getDescription());
            preparedStatement.setString(7, this.stockDescriptionRow.getCountry());
            preparedStatement.setString(8, this.stockDescriptionRow.getSector());
            preparedStatement.setString(9, this.stockDescriptionRow.getIndustry());
            preparedStatement.setString(10, this.stockDescriptionRow.getFiscalYearEnd());
            preparedStatement.setString(11, this.stockDescriptionRow.getLatestQuarter());
            preparedStatement.setString(12, this.stockDescriptionRow.getMarketCapitalization());
            preparedStatement.setString(13, this.stockDescriptionRow.getEbitda());
            preparedStatement.setString(14, this.stockDescriptionRow.getPeRatio());
            preparedStatement.setString(15, this.stockDescriptionRow.getPegRatio());
            preparedStatement.setString(16, this.stockDescriptionRow.getBookValue());
            preparedStatement.setString(17, this.stockDescriptionRow.getDividendPerShare());
            preparedStatement.setString(18, this.stockDescriptionRow.getDividendYield());
            preparedStatement.setString(19, this.stockDescriptionRow.getEps());
            preparedStatement.setString(20, this.stockDescriptionRow.getRevenuePerShareTTM());
            preparedStatement.setString(21, this.stockDescriptionRow.getProfitMargin());
            preparedStatement.setString(22, this.stockDescriptionRow.getOperatingMarginTTM());
            preparedStatement.setString(23, this.stockDescriptionRow.getReturnOnAssetsTTM());
            preparedStatement.setString(24, this.stockDescriptionRow.getReturnOnEquityTTM());
            preparedStatement.setString(25, this.stockDescriptionRow.getRevenueTTM());
            preparedStatement.setString(26, this.stockDescriptionRow.getGrossProfitTTM());
            preparedStatement.setString(27, this.stockDescriptionRow.getDilutedEPSTTM());
            preparedStatement.setString(28, this.stockDescriptionRow.getQuarterlyEarningsGrowthYOY());
            preparedStatement.setString(29, this.stockDescriptionRow.getQuarterlyRevenueGrowthYOY());
            preparedStatement.setString(30, this.stockDescriptionRow.getAnalystTargetPrice());
            preparedStatement.setString(31, this.stockDescriptionRow.getTrailingPE());
            preparedStatement.setString(32, this.stockDescriptionRow.getForwardPE());
            preparedStatement.setString(33, this.stockDescriptionRow.getPriceToSalesRatioTTM());
            preparedStatement.setString(34, this.stockDescriptionRow.getPriceToBookRatio());
            preparedStatement.setString(35, this.stockDescriptionRow.getEvToRevenue());
            preparedStatement.setString(36, this.stockDescriptionRow.getEvToEBITDA());
            preparedStatement.setString(37, this.stockDescriptionRow.getBeta());
            preparedStatement.setString(38, this.stockDescriptionRow.get_52WeekHigh());
            preparedStatement.setString(39, this.stockDescriptionRow.get_52WeekLow());
            preparedStatement.setString(40, this.stockDescriptionRow.get_50DayMovingAverage());
            preparedStatement.setString(41, this.stockDescriptionRow.get_200DayMovingAverage());
            preparedStatement.setString(42, this.stockDescriptionRow.getSharesOutstanding());
            preparedStatement.setString(43, this.stockDescriptionRow.getDividendDate());
            preparedStatement.setString(44, this.stockDescriptionRow.getExDividendDate());


            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
            System.out.println(this.stockDescriptionRow.getSymbol());
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
