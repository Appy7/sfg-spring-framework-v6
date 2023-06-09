package com.sfg.course.spring6webapp.bootstrap;

import com.sfg.course.spring6webapp.repository2.BeerRepository;
import com.sfg.course.spring6webapp.repository2.CustomerRepository;
import com.sfg.course.spring6webapp.service.BeerCSVService;
import com.sfg.course.spring6webapp.service.BeerCSVServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BeerCSVServiceImpl.class)
class BootstrapBeerDataTest {
    //import is required as it is a test splice,testing one layer

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;


    BootstrapBeerAndCustomerData bootstrapBeerData;
    @Autowired
    BeerCSVService beerCSVService;


    @BeforeEach
    void setup(){
        bootstrapBeerData=new BootstrapBeerAndCustomerData(beerRepository,customerRepository,beerCSVService);
    }
    @Test
    void testBootstrapData() throws Exception {

        bootstrapBeerData.run(null);

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);

    }



}