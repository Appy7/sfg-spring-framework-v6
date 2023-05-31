package com.sfg.course.spring6webapp.Controller;

import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beerservice.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController

public class BeerController {

    public static final String BEER_PATH = "/ap1/v1/beer";

    public static final String BEER_PATH_WITH_ID = BEER_PATH+"/{beerId}";
    private final BeerService beerService;



    @GetMapping(BEER_PATH)
    public List<BeerDTO> getBeers(){
        return beerService.getListOfBeers();
    }
    @GetMapping(BEER_PATH_WITH_ID)
    public BeerDTO getBeerByIdInController(@PathVariable("beerId") UUID id){
        log.debug("Inside Controller");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> postBeer(@RequestBody BeerDTO beer){
        BeerDTO savedBeer=beerService.saveBeer(beer);
        HttpHeaders headers=new HttpHeaders();
        headers.add("location","/ap1/v1/beer"+savedBeer.getId());
        //return new ResponseEntity<>(savedBeer, HttpStatus.CREATED);
        return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
    }
    @PutMapping(BEER_PATH_WITH_ID)
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID id,@RequestBody BeerDTO beer){
        beerService.updateBeer(id,beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_WITH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID id){
        beerService.deleteBeer(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
