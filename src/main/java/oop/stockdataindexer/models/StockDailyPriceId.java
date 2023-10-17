package oop.stockdataindexer.models;

import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
public class StockDailyPriceId implements Serializable {
    private String symbol;
    private Date timestamp;
}
