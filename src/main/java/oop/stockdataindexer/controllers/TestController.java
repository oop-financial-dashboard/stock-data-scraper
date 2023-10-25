package oop.stockdataindexer.controllers;

import oop.stockdataindexer.models.StockListing;
import oop.stockdataindexer.services.CSVReaderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
public class TestController {


    @RequestMapping("/hello")
    @ResponseBody
    private String hello() throws IOException {
        CSVReaderService x = new CSVReaderService();
        ArrayList<StockListing> y = x.readCSV("src/main/resources/top50.csv");
        return y.toString();
    }
}