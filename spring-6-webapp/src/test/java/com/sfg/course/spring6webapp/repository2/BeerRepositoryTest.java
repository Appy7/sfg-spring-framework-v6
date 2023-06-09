package com.sfg.course.spring6webapp.repository2;

import com.sfg.course.spring6webapp.service.BeerCSVServiceImpl;
import com.sfg.course.spring6webapp.bootstrap.BootstrapBeerAndCustomerData;
import com.sfg.course.spring6webapp.model.BeerStyle;
import com.sfg.course.spring6webapp.entities.Beer;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Import({BootstrapBeerAndCustomerData.class, BeerCSVServiceImpl.class})
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void testBeerRepoByNmae() {
        Page<Beer> beerList= beerRepository.findAllByBeerNameIsLikeIgnoreCase("IPA", null);
        assertThat(beerList.getContent().size()).isEqualTo(1);
    }

    @Test
    void testBeerRepo(){

        assertThrows(ConstraintViolationException.class,()->{
            Beer beer=beerRepository.save(Beer.builder()
                .beerName("Appy Repodsfdsddsdsvsvsvsvfdvfdvfvdfvdfvfdvfdvfvfv")
                .beerStyle(BeerStyle.PORTER)
                .upc("9809")
                .price(new BigDecimal("8.8")).build());
            beerRepository.flush();
        });



        //flush() would save the changes into the database.that's why this will fail if validation on all fields are not applied.
        //beerRepository.flush();

        System.out.print("test passed");
    }

}