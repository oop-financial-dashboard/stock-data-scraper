package oop.stockdataindexer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class StockDailyPriceRow {
    private String symbol;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private LocalDate timestamp;
}
