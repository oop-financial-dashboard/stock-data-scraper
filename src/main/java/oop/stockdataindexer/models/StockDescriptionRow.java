package oop.stockdataindexer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "stock_description")
public class StockDescriptionRow {
    @Id
    @JsonProperty("Symbol")
    private String symbol;
    @JsonProperty("Exchange")
    private String exchange;
    @JsonProperty("Currency")
    private String currency;
    @JsonProperty("AssetType")
    private String assetType;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Sector")
    private String sector;
    @JsonProperty("Industry")
    private String industry;
    @JsonProperty("FiscalYearEnd")
    private String fiscalYearEnd;
    @JsonProperty("LatestQuarter")
    private String latestQuarter;
    @JsonProperty("MarketCapitalization")
    private String marketCapitalization;
    @JsonProperty("EBITDA")
    private String ebitda;
    @JsonProperty("PERatio")
    private String peRatio;
    @JsonProperty("PEGRatio")
    private String pegRatio;
    @JsonProperty("BookValue")
    private String bookValue;
    @JsonProperty("DividendPerShare")
    private String dividendPerShare;
    @JsonProperty("DividendYield")
    private String dividendYield;
    @JsonProperty("EPS")
    private String eps;
    @JsonProperty("RevenuePerShareTTM")
    private String revenuePerShareTTM;
    @JsonProperty("ProfitMargin")
    private String profitMargin;
    @JsonProperty("OperatingMarginTTM")
    private String operatingMarginTTM;
    @JsonProperty("ReturnOnAssetsTTM")
    private String returnOnAssetsTTM;
    @JsonProperty("ReturnOnEquityTTM")
    private String returnOnEquityTTM;
    @JsonProperty("RevenueTTM")
    private String revenueTTM;
    @JsonProperty("GrossProfitTTM")
    private String grossProfitTTM;
    @JsonProperty("DilutedEPSTTM")
    private String dilutedEPSTTM;
    @JsonProperty("QuarterlyEarningsGrowthYOY")
    private String quarterlyEarningsGrowthYOY;
    @JsonProperty("QuarterlyRevenueGrowthYOY")
    private String quarterlyRevenueGrowthYOY;
    @JsonProperty("AnalystTargetPrice")
    private String analystTargetPrice;
    @JsonProperty("TrailingPE")
    private String trailingPE;
    @JsonProperty("ForwardPE")
    private String forwardPE;
    @JsonProperty("PriceToSalesRatioTTM")
    private String priceToSalesRatioTTM;
    @JsonProperty("PriceToBookRatio")
    private String priceToBookRatio;
    @JsonProperty("EVToRevenue")
    private String evToRevenue;
    @JsonProperty("EVToEBITDA")
    private String evToEBITDA;
    @JsonProperty("Beta")
    private String beta;
    @JsonProperty("52WeekHigh")
    private String _52WeekHigh;
    @JsonProperty("52WeekLow")
    private String _52WeekLow;
    @JsonProperty("50DayMovingAverage")
    private String _50DayMovingAverage;
    @JsonProperty("200DayMovingAverage")
    private String _200DayMovingAverage;
    @JsonProperty("SharesOutstanding")
    private String sharesOutstanding;
    @JsonProperty("DividendDate")
    private String dividendDate;
    @JsonProperty("ExDividendDate")
    private String exDividendDate;
}
