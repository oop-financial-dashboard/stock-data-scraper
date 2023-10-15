package oop.stockdataindexer.services;
import oop.stockdataindexer.models.StockListing;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
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
                StockListing stock = new StockListing();
                String ticker = csvRecord.get("symbol");
                stock.setSymbol(ticker);
                String name = csvRecord.get("name");
                stock.setName(name);
                String exchange = csvRecord.get("exchange");
                stock.setExchange(exchange);
                String assetType = csvRecord.get("assetType");
                stock.setAssetType(assetType);
                String ipoDate = csvRecord.get("ipoDate");
                stock.setIpoDate(ipoDate);
                String delistingDate = csvRecord.get("delistingDate");
                stock.setDelistingDate(delistingDate);
                String status = csvRecord.get("status");
                stock.setStatus(status);
                stockSymbols.add(stock);
            }
        }
        return stockSymbols;
    }

}
