package com.sfg.course.spring6webapp.bootstrap;

import com.sfg.course.spring6webapp.beerrepository.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class BootstrapBeerDataTest {

    @Autowired
    BeerRepository beerRepository;

    BootstrapBeerData bootstrapBeerData;


    @BeforeEach
    void setup(){
        bootstrapBeerData=new BootstrapBeerData(beerRepository);
    }
    @Test
    void testBootstrapData() throws Exception {

        bootstrapBeerData.run(null);

        assertThat(beerRepository.count()).isEqualTo(3);
 }



}