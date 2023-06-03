package com.sfg.course.spring6webapp.beerservice;

import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beer.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap=new HashMap<>();
        BeerDTO b1= BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("ABC")
                .beerStyle(BeerStyle.LAGER)
                .upc("312")
                .price(new BigDecimal("12.56"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        BeerDTO b2= BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(2)
                .beerName("vsv")
                .beerStyle(BeerStyle.AMBER_ALE)
                .upc("99")
                .price(new BigDecimal("12.55"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        BeerDTO b3= BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(3)
                .beerName("bdb")
                .beerStyle(BeerStyle.STOUT)
                .upc("323")
                .price(new BigDecimal("12.50"))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        beerMap.put(b1.getId(),b1);
        beerMap.put(b2.getId(),b2);
        beerMap.put(b3.getId(),b3);

    }
    @Override
    public List<BeerDTO> getListOfBeers(){
      return new ArrayList<>(beerMap.values());

}
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Inside service");
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        BeerDTO savedBeer= BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(beer.getVersion())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .quantityOnHand(beer.getQuantityOnHand())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer.getId(),savedBeer);
        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer) {
       BeerDTO existingBeer= beerMap.get(id);
       existingBeer.setBeerName(beer.getBeerName());
       existingBeer.setBeerStyle(beer.getBeerStyle());
       existingBeer.setPrice(beer.getPrice());
       existingBeer.setVersion(beer.getVersion());
       existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
       existingBeer.setUpc(beer.getUpc());
       beerMap.put(existingBeer.getId(),existingBeer);

      return Optional.of(existingBeer);
    }

    @Override
    public Boolean deleteBeer(UUID id) {

        beerMap.remove(id);
        return true;
    }
}
