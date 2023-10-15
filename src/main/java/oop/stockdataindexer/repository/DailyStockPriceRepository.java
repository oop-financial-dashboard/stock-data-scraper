package oop.stockdataindexer.repository;

import oop.stockdataindexer.entity.StockDailyPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface DailyStockPriceRepository extends JpaRepository<StockDailyPriceEntity, Long> {
    //Create
    @Modifying
    @Query(value = """
             INSERT INTO StockDailyPrice (symbol, open, high, low, close, volume, timestamp)
                    VALUES (:symbol, :open, :high, :low, :close, :volume, :timestamp)
            """, nativeQuery = true)
    int insertStockDailyPrice(@Param("symbol") String symbol,
                              @Param("open") String open,
                              @Param("high") String high,
                              @Param("low") String low,
                              @Param("close") String close,
                              @Param("volume") String volume,
                              @Param("timestamp") Timestamp timestamp);

}
