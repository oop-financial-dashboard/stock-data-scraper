package oop.stockdataindexer.services;

import lombok.Data;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.StockDescriptionRow;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.services.postgres.InsertStockDescriptionService;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Data
public class StockDescriptionScrapingService {

    public void ScrapeStockDescriptions() throws IOException {
        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> Stocks = x.readCSV("src/main/resources/listing_status.csv");
        Stocks.forEach((stock)->{
            String symbol = stock.getSymbol();
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = String.format("https://www.alphavantage.co/query?function=OVERVIEW&symbol=%s&apikey=PGMGQLXTQWX42V8V", symbol);
            StockDescriptionRow res = restTemplate.getForObject(apiUrl, StockDescriptionRow.class);
            //TODO: throw error
            if(res == null){
                System.out.println("Failed to retireve daily stock price data from AlphaVantage");
                System.out.println(stock.getSymbol());
                return;
            }
            InsertStockDescriptionService insertStockDescriptionService = new InsertStockDescriptionService(res);
            //TODO: write exceptions
            try {
                insertStockDescriptionService.insertRecord();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
