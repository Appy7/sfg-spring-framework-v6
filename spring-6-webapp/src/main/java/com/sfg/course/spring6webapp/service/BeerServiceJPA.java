package com.sfg.course.spring6webapp.service;

import com.sfg.course.spring6webapp.entities.Beer;
import com.sfg.course.spring6webapp.model.BeerDTO;
import com.sfg.course.spring6webapp.repository2.BeerRepository;
import com.sfg.course.spring6webapp.mapper.BeerMapper;
import com.sfg.course.spring6webapp.model.BeerStyle;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private static final Integer DEFAULT_PAGE_NO=0;
    private static final Integer DEFAULT_PAGE_SIZE=25;
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.
                beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public Page<BeerDTO> getListOfBeers(String beerName, BeerStyle beerStyle, Boolean showInventory,
                                        Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest=getPageRequest(pageNumber,pageSize);

        Page<Beer> beerPage;
        if (StringUtils.hasText(beerName) && beerStyle==null){
            beerPage=getBeerListByBeerName(beerName,pageRequest);
        }
        else if (!StringUtils.hasText(beerName) && beerStyle!=null){
            beerPage=getBeerListByBeerStyle(beerStyle,pageRequest);
        } else if (StringUtils.hasText(beerName) && beerStyle!=null) {
            beerPage=getBeerListByBeerNameAndBeerStyle(beerName,beerStyle,pageRequest);

        } else {
            beerPage= beerRepository.findAll(pageRequest);
        }

        if(showInventory!=null && !showInventory){
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }
        return beerPage.map(beerMapper::beerToBeerDto);
        //return (Page<BeerDTO>) beerPage.stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
    }

    private PageRequest getPageRequest(Integer pageNumber, Integer pageSize) {

        Integer queryPageNumber;
        Integer queryPageSize;

        if (pageNumber!=null && pageNumber>0)
        {
            queryPageNumber=pageNumber-1;
        }else {queryPageNumber=DEFAULT_PAGE_NO;}

        if (pageSize==null) {
            queryPageSize=DEFAULT_PAGE_SIZE;
        }else{
            if (pageSize>1000)
            {queryPageSize=1000;}
            else
            {queryPageSize=pageSize;}
        }

        Sort sort=Sort.by(Sort.Order.asc("beerName"));
       return PageRequest.of(queryPageNumber,queryPageSize,sort);
    }

    private Page<Beer> getBeerListByBeerNameAndBeerStyle(String beerName, BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameAndBeerStyle(beerName,beerStyle, null);
    }

    private Page<Beer> getBeerListByBeerStyle(BeerStyle beerStyle, PageRequest pageRequest) {
        return beerRepository.findAllByBeerStyle(beerStyle, null);
    }

    private Page<Beer> getBeerListByBeerName(String beerName, PageRequest pageRequest) {
        return beerRepository.findAllByBeerNameIsLikeIgnoreCase("%" +beerName+ "%", null);
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer) {

        //this occurs outside the transaction as detached object
        //return  Optional.of(beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer))));

        //this occurs within transaction,version remaining same.Hibernate does not throw ObjectOptimisticLockingFailureException
        AtomicReference<Optional<BeerDTO>> atomicReference=new AtomicReference<>();
                beerRepository.findById(id).ifPresentOrElse(
                foundedBeer->{
                foundedBeer.setBeerName(beer.getBeerName());
                foundedBeer.setBeerStyle(beer.getBeerStyle());
                foundedBeer.setUpc(beer.getUpc());
                foundedBeer.setQuantityOnHand(beer.getQuantityOnHand());
                foundedBeer.setPrice(beer.getPrice());
                atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundedBeer))))
                ;}, ()->atomicReference.set(Optional.empty()));

        return atomicReference.get();
     }

    @Override
    public Boolean deleteBeer(UUID id) {

    if(beerRepository.existsById(id)) {
        beerRepository.deleteById(id);
        return  true;
    }
        return false;
    }
}
