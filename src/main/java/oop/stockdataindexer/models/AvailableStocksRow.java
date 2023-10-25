package oop.stockdataindexer.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableStocksRow {
    private String symbol;
    private String name;
    private LocalDate lastIndexedDate;
}
