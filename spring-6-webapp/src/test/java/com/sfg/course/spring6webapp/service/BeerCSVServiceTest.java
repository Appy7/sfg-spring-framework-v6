package com.sfg.course.spring6webapp.service;

import com.sfg.course.spring6webapp.model.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerCSVServiceTest {
    BeerCSVService beerCSVService=new BeerCSVServiceImpl();

    @Test
    void convertCSVToPOJO() throws FileNotFoundException {
        File file =ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> records=beerCSVService.convertCSVToPOJO(file);
        System.out.println(records.size());
        assertThat(records.size()).isGreaterThan(0);
    }
}