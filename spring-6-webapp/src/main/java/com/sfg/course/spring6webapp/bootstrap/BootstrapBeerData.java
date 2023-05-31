package com.sfg.course.spring6webapp.bootstrap;

import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beer.BeerStyle;
import com.sfg.course.spring6webapp.beerrepository.BeerRepository;
import com.sfg.course.spring6webapp.entities.Beer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Component
@AllArgsConstructor
public class BootstrapBeerData implements CommandLineRunner {
    BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        fillBeerData();

    }

    public void fillBeerData() {
       if(beerRepository.count()==0){
            Beer b1= Beer.builder()
                    .beerName("jhjkh")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("312")
                    .price(new BigDecimal("12.56"))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            Beer b2= Beer.builder()
                    .beerName("bvbnv")
                    .beerStyle(BeerStyle.AMBER_ALE)
                    .upc("99")
                    .price(new BigDecimal("12.55"))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            Beer b3= Beer.builder()
                    .beerName("sdzasda")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("323")
                    .price(new BigDecimal("12.50"))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(b1);
            beerRepository.save(b2);
            beerRepository.save(b3);

        }

    }
}
