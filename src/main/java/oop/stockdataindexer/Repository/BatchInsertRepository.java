package oop.stockdataindexer.Repository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import oop.stockdataindexer.models.StockDailyPriceRow;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@AllArgsConstructor
public class BatchInsertRepository {
    private JdbcTemplate jdbcTemplate;

    public BatchInsertRepository() {
        this.jdbcTemplate = new JdbcTemplate(dataSource());
    }

    public void saveAll(List<StockDailyPriceRow> dailyPriceRowList) {
        this.jdbcTemplate = new JdbcTemplate(dataSource());
        System.out.printf("Starting batch insert at ---> %s\n", LocalDateTime.now());
        jdbcTemplate.batchUpdate("INSERT INTO stock_daily_price(symbol, open, high, low, close, volume, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?)",
                dailyPriceRowList,
                100,
                (PreparedStatement ps, StockDailyPriceRow row) -> {
                    ps.setString(1, row.getSymbol());
                    ps.setString(2, row.getOpen());
                    ps.setString(3, row.getHigh());
                    ps.setString(4, row.getLow());
                    ps.setString(5, row.getClose());
                    ps.setString(6, row.getVolume());
                    ps.setDate(7, Date.valueOf(row.getTimestamp()));
                });

    }

    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://ep-tight-bar-26500515-pooler.ap-southeast-1.aws.neon.tech/neondb?prepareThreshold=0");
        dataSource.setUsername("sticker99");
        dataSource.setPassword("4WfK9pJnaOoD");
        return dataSource;
    }
}
