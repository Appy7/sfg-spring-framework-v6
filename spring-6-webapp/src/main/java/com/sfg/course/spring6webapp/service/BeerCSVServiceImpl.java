package com.sfg.course.spring6webapp.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.sfg.course.spring6webapp.model.BeerCSVRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
public class BeerCSVServiceImpl implements BeerCSVService {


    @Override
    public List<BeerCSVRecord> convertCSVToPOJO(File csvFile) throws FileNotFoundException {

       try{
           List<BeerCSVRecord> beerCsvRecords=new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(csvFile))
                .withType(BeerCSVRecord.class)
                .build().parse();
                return beerCsvRecords;
            }
       catch (FileNotFoundException e){
           throw new RuntimeException((e));
       }
    }
}
