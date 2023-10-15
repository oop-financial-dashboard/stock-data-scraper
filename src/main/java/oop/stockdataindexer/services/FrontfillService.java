package oop.stockdataindexer.services;
import lombok.Data;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.services.postgres.CreateStockDailyPricesTableService;
import oop.stockdataindexer.services.postgres.InsertStockDailyPriceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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
            String apiUrl = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=PGMGQLXTQWX42V8V&outputsize=compact", symbol);
            AlphaVantageDailyPrice res = restTemplate.getForObject(apiUrl, AlphaVantageDailyPrice.class);
            //TODO: throw error
            if(res == null || res.getMetaData() == null){
                System.out.println("Failed to retireve daily stock price data from AlphaVantage");
                System.out.println(stock.getSymbol());
                return;
            }

            String lastRefreshed = res.getMetaData().getLastRefreshed();
            String open = res.getTimeSeriesDailyMap().get(lastRefreshed).getOpen();
            String high = res.getTimeSeriesDailyMap().get(lastRefreshed).getHigh();
            String low = res.getTimeSeriesDailyMap().get(lastRefreshed).getLow();
            String close = res.getTimeSeriesDailyMap().get(lastRefreshed).getClose();
            String volume = res.getTimeSeriesDailyMap().get(lastRefreshed).getVolume();
            String dateFormat = res.getMetaData().getLastRefreshed() + " 00:00:00";
            Timestamp date = Timestamp.valueOf(dateFormat);

            StockDailyPriceRow row = new StockDailyPriceRow();
            row.setSymbol(symbol);
            row.setOpen(open);
            row.setHigh(high);
            row.setLow(low);
            row.setClose(close);
            row.setVolume(volume);
            row.setTimestamp(date);
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
