package oop.stockdataindexer.services;

import lombok.Data;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.StockDescriptionRow;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.services.postgres.InsertStockDescriptionService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Data
public class StockDescriptionScrapingService {

    public void ScrapeStockDescriptions() throws IOException {
        System.out.println("DESCRIPTION SCRAPPER RUNNING");
        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> Stocks = x.readCSV("src/main/resources/top50.csv");
        Stocks.forEach((stock)->{
            String symbol = stock.getSymbol();
            RestTemplate restTemplate = new RestTemplate();
            String apiKey = "PGMGQLXTQWX42V8V";
            String apiUrl = String.format("https://www.alphavantage.co/query?function=OVERVIEW&symbol=%s&apikey=%s", symbol, apiKey);
            StockDescriptionRow res = restTemplate.getForObject(apiUrl, StockDescriptionRow.class);
            if(res == null || res.getSymbol() == null){
                System.out.print(res);
                System.out.printf("Failed to retrieve stock description data: %s from AlphaVantage%n", stock.getSymbol());
                return;
            }
            InsertStockDescriptionService insertStockDescriptionService = new InsertStockDescriptionService(res);
            try {
                insertStockDescriptionService.insertRecord();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
