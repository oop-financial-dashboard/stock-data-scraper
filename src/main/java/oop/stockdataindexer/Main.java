package oop.stockdataindexer;

import oop.stockdataindexer.services.BackfillService;
import oop.stockdataindexer.services.StockDescriptionScrapingService;
import oop.stockdataindexer.services.postgres.CreateStockDescriptionTableService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import oop.stockdataindexer.services.postgres.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableScheduling
@Configuration
public class Main extends SpringBootServletInitializer {

	@Bean
	public BackfillService backfillService() {
		return new BackfillService();
	}
	@Bean
	public StockDescriptionScrapingService stockDescriptionScrapingService(){
		return new StockDescriptionScrapingService();
	}

//	@Bean
//	public CreateStockDailyPricesTableService createStockDailyPricesTableService(){
//		return new CreateStockDailyPricesTableService();
//	}
//
//	@Bean
//	public CreateStockDescriptionTableService createStockDescriptionTableService(){
//		return new CreateStockDescriptionTableService();
//	}
	public static void main(String[] args) throws SQLException, IOException {

		SpringApplication.run(Main.class, args);
		CreateStockDailyPricesTableService createStockDailyPricesTable = new CreateStockDailyPricesTableService();
		CreateStockDescriptionTableService createStockDescriptionTable = new CreateStockDescriptionTableService();
		BackfillService backfillService = new BackfillService();
		StockDescriptionScrapingService stockDescriptionScrapingService = new StockDescriptionScrapingService();
		createStockDailyPricesTable.createTable();
		createStockDescriptionTable.createTable();
		backfillService.backfill();
		stockDescriptionScrapingService.ScrapeStockDescriptions();
	}

}
