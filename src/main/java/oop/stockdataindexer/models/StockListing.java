package oop.stockdataindexer.models;

import lombok.Data;

@Data
public class StockListing {
    private String symbol;
    private String name;
    private String exchange;
    private String assetType;
    private String ipoDate;
    private String delistingDate;
    private String status;

}
