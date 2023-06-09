package com.sfg.course.spring6webapp.service;

import com.sfg.course.spring6webapp.model.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface BeerCSVService {
    List<BeerCSVRecord> convertCSVToPOJO(File csvFile) throws FileNotFoundException;
}
