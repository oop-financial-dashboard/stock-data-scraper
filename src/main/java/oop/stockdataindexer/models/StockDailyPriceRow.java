package oop.stockdataindexer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@IdClass(StockDailyPriceId.class)
public class StockDailyPriceRow {
    @Id
    private String symbol;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    @Id
    private LocalDate timestamp;
}
