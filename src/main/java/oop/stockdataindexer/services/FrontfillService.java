package oop.stockdataindexer.services;
import lombok.Data;
import oop.stockdataindexer.Repository.BatchInsertRepository;
import oop.stockdataindexer.models.AvailableStocksRow;
import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.models.StockDailyPriceRow;
import oop.stockdataindexer.models.alphaVantage.AlphaVantageDailyPrice;
import oop.stockdataindexer.models.alphaVantage.TimeSeriesDaily;
import oop.stockdataindexer.services.postgres.GetLastIndexedDate;
import oop.stockdataindexer.services.postgres.UpdateAvailableStocksService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Component
public class FrontfillService {

//    @Scheduled(cron = "* * * ? * MON-FRI")
    @Scheduled(cron = "0 0 7 * * *")
    public void frontfill() throws IOException, SQLException {
        System.out.println("FRONTFILL IS RUNNING");
        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> Stocks = x.readCSV("src/main/resources/top50.csv");
        GetLastIndexedDate getLastIndexedDate = new GetLastIndexedDate();
        LocalDate lastIndexedDate = getLastIndexedDate.selectRecord();
        Stocks.forEach((stock) -> {
            final List<StockDailyPriceRow> stockDailyPriceRowList = new ArrayList<>();
            String symbol = stock.getSymbol();
            LocalDate newLastIndexedDate = null;
            boolean firstIteration = true;
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
            Map<String, TimeSeriesDaily> timeSeriesDailyMap = res.getTimeSeriesDailyMap();
            for (Map.Entry<String, TimeSeriesDaily> entry : timeSeriesDailyMap.entrySet()) {
                if(firstIteration){
                    newLastIndexedDate = LocalDate.parse(entry.getKey());
                    firstIteration = false;
                }
                TimeSeriesDaily stockData = entry.getValue();
                LocalDate formattedDate = LocalDate.parse(entry.getKey());
                if(formattedDate.isBefore(lastIndexedDate)|| formattedDate.equals(lastIndexedDate)){
                    continue;
                }
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
            //TODO: write exceptions
            try {
                BatchInsertRepository batchInsertRepository = new BatchInsertRepository();
                Long startTime = System.nanoTime();
                batchInsertRepository.saveAll(stockDailyPriceRowList);
                Long endTime = System.nanoTime();
                Long timeElapse = (endTime - startTime) / 1000000000;
                AvailableStocksRow availableStocksRow = AvailableStocksRow.builder().symbol(symbol).name(stock.getName()).lastIndexedDate(newLastIndexedDate).build();
                UpdateAvailableStocksService updateAvailableStocksService = new UpdateAvailableStocksService(availableStocksRow);
                updateAvailableStocksService.updateLastIndexedDate();
                System.out.printf("Time taken for the batch insert in seconds ---> %d\n", timeElapse);
            } catch (Exception e) {
                System.out.println(e.getCause());
            }
        });

    }

}
