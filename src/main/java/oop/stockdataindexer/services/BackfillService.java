package oop.stockdataindexer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import oop.stockdataindexer.Repository.BatchInsertRepository;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.models.alphaVantage.TimeSeriesDaily;
import oop.stockdataindexer.services.postgres.InsertStockDailyPriceService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BackfillService {
    public BackfillService() {
    }

    public void backfill() throws IOException, SQLException, JsonProcessingException {
        System.out.println("BACKFILL SCRAPPER RUNNING");
        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> Stocks = x.readCSV("src/main/resources/listing_status.csv");

        Stocks.forEach((stock) -> {
            final List<StockDailyPriceRow> stockDailyPriceRowList = new ArrayList<>();
            String symbol = stock.getSymbol();
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
                for (Map.Entry<String, TimeSeriesDaily> entry : timeSeriesDailyMap.entrySet()) {
                    if (firstIteration) {
                        firstIteration = false;
                        continue;
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

                    //TODO: write exceptions

                }

                try {
                    BatchInsertRepository batchInsertRepository = new BatchInsertRepository();
                    Long startTime = System.nanoTime();
                    batchInsertRepository.saveAll(stockDailyPriceRowList);
                    Long endTime = System.nanoTime();
                    Long timeElapse = (endTime - startTime)/1000000000;
                    System.out.printf("Time taken for the batch insert in seconds ---> %d\n", timeElapse);
                } catch (Exception e) {
                    System.out.println(e.getCause());
                }
        });

    }


}
