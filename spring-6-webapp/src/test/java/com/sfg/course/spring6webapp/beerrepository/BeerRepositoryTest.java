package com.sfg.course.spring6webapp.beerrepository;

import com.sfg.course.spring6webapp.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerRepo(){
        Beer beer=beerRepository.save(Beer.builder().beerName("Appy Repo").build());
        assertThat(beer.getId()).isNotNull();
        assertThat(beer).isNotNull();
        System.out.print("test passed");
    }

}