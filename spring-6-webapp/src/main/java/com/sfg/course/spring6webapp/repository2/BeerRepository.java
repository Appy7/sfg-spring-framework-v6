package com.sfg.course.spring6webapp.repository2;

import com.sfg.course.spring6webapp.entities.Beer;
import com.sfg.course.spring6webapp.model.BeerStyle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@RepositoryRestResource(path = "beer")
public interface BeerRepository extends JpaRepository<Beer, UUID> {

   Page<Beer> findAllByBeerNameIsLikeIgnoreCase(String beerName, Pageable pageable);
   Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);

   Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable);

   List<Beer> findByUpc(String upc);
}
