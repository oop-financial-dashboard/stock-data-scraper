package oop.stockdataindexer.services;
import lombok.Data;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.models.alphaVantage.TimeSeriesDaily;
import oop.stockdataindexer.services.postgres.CreateStockDailyPricesTableService;
import oop.stockdataindexer.services.postgres.InsertStockDailyPriceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Component
public class FrontfillService {

//    @Scheduled(cron = "* * * ? * MON-FRI")
    @Scheduled(cron = "* 30 4 * * ?")
    public void frontfill() throws IOException, SQLException {

        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> Stocks = x.readCSV("src/main/resources/listing_status.csv");

        Stocks.forEach((stock) -> {
            String symbol = stock.getSymbol();
            RestTemplate restTemplate = new RestTemplate();
            String apiKey = "2HGPQ27GH9K7PAPC";
            String apiUrl = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s&outputsize=compact", symbol, apiKey);
            AlphaVantageDailyPrice res = restTemplate.getForObject(apiUrl, AlphaVantageDailyPrice.class);
            //TODO: throw error
            if(res == null || res.getMetaData() == null){
                System.out.println("Failed to retireve daily stock price data from AlphaVantage");
                System.out.println(stock.getSymbol());
                return;
            }

            String lastRefreshed = res.getMetaData().getLastRefreshed();
            TimeSeriesDaily dailyStockData = res.getTimeSeriesDailyMap().get(lastRefreshed);
            LocalDate formattedDate = LocalDate.parse(lastRefreshed);

            StockDailyPriceRow row = StockDailyPriceRow.builder()
                    .symbol(symbol)
                    .open(dailyStockData.getOpen())
                    .close(dailyStockData.getClose())
                    .high(dailyStockData.getHigh())
                    .low(dailyStockData.getLow())
                    .volume(dailyStockData.getVolume())
                    .timestamp(formattedDate)
                    .build();
            InsertStockDailyPriceService insertStockDailyPriceService = new InsertStockDailyPriceService(row);
            //TODO: write exceptions
            try {
                insertStockDailyPriceService.insertRecord();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

    }

}
