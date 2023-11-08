package oop.stockdataindexer;

import oop.stockdataindexer.services.BackfillService;
import oop.stockdataindexer.services.FrontfillService;
import oop.stockdataindexer.services.StockDescriptionScrapingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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

	public static void main(String[] args) throws IOException, SQLException {

		SpringApplication.run(Main.class, args);
		FrontfillService frontfillService = new FrontfillService();
//		BackfillService backfillService = new BackfillService();
		StockDescriptionScrapingService stockDescriptionScrapingService = new StockDescriptionScrapingService();
//		backfillService.backfill();
//		stockDescriptionScrapingService.ScrapeStockDescriptions();
		frontfillService.frontfill();
	}

}
