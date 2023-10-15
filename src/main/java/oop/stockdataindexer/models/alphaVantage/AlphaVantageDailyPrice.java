package oop.stockdataindexer.models.alphaVantage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import oop.stockdataindexer.models.alphaVantage.Metadata;

import java.util.Map;

@Data
public class AlphaVantageDailyPrice {
    @JsonProperty("Meta Data")
    private Metadata metaData;

    @JsonProperty("Time Series (Daily)")
    private Map<String, TimeSeriesDaily> timeSeriesDailyMap;
}
