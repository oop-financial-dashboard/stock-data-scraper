package oop.stockdataindexer.services;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.StockListing;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CSVReaderService {

    public ArrayList<StockListing> readCSV(String path) throws IOException {
        ArrayList<StockListing> stockSymbols = new ArrayList<>();

        String[] HEADERS = { "symbol", "name", "exchange", "assetType", "ipoDate", "delistingDate", "status"};

        // Process data: Split CSV lines, filter, transform, and create NYSETicker objects
        try (Reader reader = new FileReader(path);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(true).build())) {

            for (CSVRecord csvRecord : csvParser) {
                StockListing stock = StockListing.builder()
                        .symbol(csvRecord.get("symbol"))
                        .name(csvRecord.get("name"))
                        .exchange(csvRecord.get("exchange"))
                        .assetType(csvRecord.get("assetType"))
                        .ipoDate(csvRecord.get("ipoDate"))
                        .delistingDate(csvRecord.get("delistingDate"))
                        .status(csvRecord.get("status"))
                        .build();
                stockSymbols.add(stock);
            }
        }
        return stockSymbols;
    }

    public ArrayList<StockDailyPriceRow> readStockPriceCSV(String path) throws IOException {
        ArrayList<StockDailyPriceRow> stocksHistorical = new ArrayList<>();

        String[] HEADERS = { "symbol", "open", "high", "low", "close", "volume", "timestamp"};

        try (Reader reader = new FileReader(path);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder().setHeader(HEADERS).setSkipHeaderRecord(true).build())) {

            for (CSVRecord csvRecord : csvParser) {
                StockDailyPriceRow stockData = StockDailyPriceRow.builder()
                        .symbol(csvRecord.get("symbol"))
                        .open(csvRecord.get("open"))
                        .high(csvRecord.get("high"))
                        .low(csvRecord.get("low"))
                        .close(csvRecord.get("close"))
                        .volume(csvRecord.get("volume"))
                        .timestamp(LocalDate.parse(csvRecord.get("timestamp")))
                        .build();
                stocksHistorical.add(stockData);
            }
        }
        return stocksHistorical;
    }

}
