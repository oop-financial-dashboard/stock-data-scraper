package oop.stockdataindexer.services;

import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.models.alphaVantage.TimeSeriesDaily;
import oop.stockdataindexer.services.postgres.InsertStockDailyPriceService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

@Service
public class BackfillService {
    public void backfill() throws IOException, SQLException {

        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> Stocks = x.readCSV("src/main/resources/listing_status.csv");

        Stocks.forEach((stock) -> {
            String symbol = stock.getSymbol();
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=PGMGQLXTQWX42V8V&outputsize=full", symbol);
            AlphaVantageDailyPrice res = restTemplate.getForObject(apiUrl, AlphaVantageDailyPrice.class);
            //TODO: throw error
            if(res == null || res.getMetaData() == null){
                System.out.println("Failed to retireve daily stock price data from AlphaVantage");
                System.out.println(stock.getSymbol());
                return;
            }
            Map<String, TimeSeriesDaily> timeSeriesDailyMap = res.getTimeSeriesDailyMap();
            for (Map.Entry<String, TimeSeriesDaily> entry : timeSeriesDailyMap.entrySet()){
                String date = entry.getKey();
                TimeSeriesDaily stockData = entry.getValue();
                String open = stockData.getOpen();
                String high = stockData.getHigh();
                String low = stockData.getLow();
                String close = stockData.getClose();
                String volume = stockData.getVolume();
                String dateWithTime = date + " 00:00:00";
                Timestamp formattedDate = Timestamp.valueOf(dateWithTime);

                StockDailyPriceRow row = new StockDailyPriceRow();
                row.setSymbol(symbol);
                row.setOpen(open);
                row.setHigh(high);
                row.setLow(low);
                row.setClose(close);
                row.setVolume(volume);
                row.setTimestamp(formattedDate);
                InsertStockDailyPriceService insertStockDailyPriceService = new InsertStockDailyPriceService(row);
                //TODO: write exceptions
                try {
                    insertStockDailyPriceService.insertRecord();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


}