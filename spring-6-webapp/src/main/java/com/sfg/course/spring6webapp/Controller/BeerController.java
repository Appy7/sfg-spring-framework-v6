package com.sfg.course.spring6webapp.Controller;

import com.sfg.course.spring6webapp.model.BeerDTO;
import com.sfg.course.spring6webapp.service.BeerService;
import com.sfg.course.spring6webapp.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/ap1/v1/beer";

    public static final String BEER_PATH_WITH_ID = BEER_PATH+"/{beerId}";
    private final BeerService beerService;



    @GetMapping(BEER_PATH)
    public Page<BeerDTO> getBeers(@RequestParam(required = false) String beerName,
                                  @RequestParam(required = false) BeerStyle beerStyle,
                                  @RequestParam(required = false) Boolean showInventory,
                                  @RequestParam(required = false) Integer pageNumber,
                                  @RequestParam(required = false) Integer pageSize){

        return beerService.getListOfBeers(beerName,beerStyle, showInventory,pageNumber,pageSize);
    }
    @GetMapping(BEER_PATH_WITH_ID)
    public BeerDTO getBeerByIdInController(@PathVariable("beerId") UUID id){
        log.debug("Inside Controller");
        return beerService.getBeerById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerDTO> postBeer(@Validated @RequestBody BeerDTO beer){
        BeerDTO savedBeer=beerService.saveBeer(beer);
        HttpHeaders headers=new HttpHeaders();
        headers.add("location","/ap1/v1/beer/"+savedBeer.getId());
        //return new ResponseEntity<>(savedBeer, HttpStatus.CREATED);
        return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
    }
    @PutMapping(BEER_PATH_WITH_ID)
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID id,@Validated @RequestBody BeerDTO beer){
        if((beerService.updateBeer(id,beer)).isEmpty()){
            throw new NotFoundException();
        };
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_WITH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID id){
       if(! beerService.deleteBeer(id)){
           throw new NotFoundException();
       }
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
