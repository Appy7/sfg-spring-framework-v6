package com.sfg.course.spring6webapp.beerservice;

import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beerrepository.BeerRepository;
import com.sfg.course.spring6webapp.bootstrap.BootstrapBeerData;
import com.sfg.course.spring6webapp.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceRepo implements BeerService{
    BeerRepository beerRepository;
    BeerMapper beerMapper;
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.ofNullable(beerMapper.
                beerToBeerDto(beerRepository.findById(id).orElse(null)));
    }

    @Override
    public List<BeerDTO> getListOfBeers() {
        return beerRepository.findAll()
                .stream().map(beerMapper::beerToBeerDto).collect(Collectors.toList());
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public void updateBeer(UUID id, BeerDTO beer) {

    }

    @Override
    public void deleteBeer(UUID id) {

    }
}
