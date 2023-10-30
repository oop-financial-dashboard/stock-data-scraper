package oop.stockdataindexer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import oop.stockdataindexer.Repository.BatchInsertRepository;
import oop.stockdataindexer.models.AvailableStocksRow;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.models.alphaVantage.TimeSeriesDaily;
import oop.stockdataindexer.services.postgres.InsertAvailableStocksService;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class BackfillService {
    public BackfillService() {
    }

    public void backfill() throws IOException, SQLException, JsonProcessingException {
        System.out.println("BACKFILL SCRAPPER RUNNING");
        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> Stocks = x.readCSV("src/main/resources/top50.csv");
        //temp variable to check for last stock indexed
        Stocks.forEach((stock) -> {
            final List<StockDailyPriceRow> stockDailyPriceRowList = new ArrayList<>();
            String symbol = stock.getSymbol();
            String name = stock.getName();
            RestTemplate restTemplate = new RestTemplate();
            String apiKey = "2HGPQ27GH9K7PAPC";
            String apiUrl = String.format("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=%s&apikey=%s&outputsize=full", symbol, apiKey);
            AlphaVantageDailyPrice res = restTemplate.getForObject(apiUrl, AlphaVantageDailyPrice.class);
            //TODO: throw error
            if (res == null || res.getMetaData() == null) {
                System.out.printf("Failed to retrieve daily stock price data: %s from AlphaVantage\n", stock.getSymbol());
                return;
            }
            boolean firstIteration = true;
            Map<String, TimeSeriesDaily> timeSeriesDailyMap = res.getTimeSeriesDailyMap();
            LocalDate lastIndexedDate = null;
            for (Map.Entry<String, TimeSeriesDaily> entry : timeSeriesDailyMap.entrySet()) {
                if (firstIteration) {
                    firstIteration = false;
                    lastIndexedDate = LocalDate.parse(entry.getKey());
                }

                TimeSeriesDaily stockData = entry.getValue();
                LocalDate formattedDate = LocalDate.parse(entry.getKey());

                StockDailyPriceRow row = StockDailyPriceRow.builder()
                        .symbol(symbol)
                        .open(stockData.getOpen())
                        .close(stockData.getClose())
                        .high(stockData.getHigh())
                        .low(stockData.getLow())
                        .volume(stockData.getVolume())
                        .timestamp(formattedDate)
                        .build();
                stockDailyPriceRowList.add(row);
            }
            try {
                BatchInsertRepository batchInsertRepository = new BatchInsertRepository();
                Long startTime = System.nanoTime();
                batchInsertRepository.saveAll(stockDailyPriceRowList);
                Long endTime = System.nanoTime();
                Long timeElapse = (endTime - startTime) / 1000000000;
                System.out.printf("Time taken for the batch insert in seconds ---> %d\n", timeElapse);
            } catch (Exception e) {
                System.out.println(e.getCause());

            }

            if (lastIndexedDate != null) {
                AvailableStocksRow availableStock = AvailableStocksRow.builder().symbol(symbol).name(name).lastIndexedDate(lastIndexedDate).build();
                InsertAvailableStocksService service = new InsertAvailableStocksService(availableStock);
                try {
                    service.insertRecord();
                } catch (SQLException e) {
                    //TODO: write exception
                    throw new RuntimeException(e);
                }
            }

        });

    }


}
