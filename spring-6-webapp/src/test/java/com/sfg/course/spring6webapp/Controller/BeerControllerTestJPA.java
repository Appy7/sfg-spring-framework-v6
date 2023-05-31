package com.sfg.course.spring6webapp.Controller;

import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beerrepository.BeerRepository;
import com.sfg.course.spring6webapp.bootstrap.BootstrapBeerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerControllerTestJPA {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerController beerController;

    @Test
    void testGetAllBeers(){
        List<BeerDTO> dtoList=beerController.getBeers();

        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testGetEmptyBeers(){

        beerRepository.deleteAll();

        List<BeerDTO> dtoList=beerController.getBeers();

        assertThat(dtoList.size()).isEqualTo(0);
    }


}