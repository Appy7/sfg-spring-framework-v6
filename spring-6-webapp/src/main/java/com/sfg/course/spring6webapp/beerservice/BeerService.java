package com.sfg.course.spring6webapp.beerservice;

import com.sfg.course.spring6webapp.beer.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDTO> getBeerById(UUID id);
    List<BeerDTO> getListOfBeers();

    BeerDTO saveBeer(BeerDTO beer);

    void updateBeer(UUID id, BeerDTO beer);

    void deleteBeer(UUID id);
}
