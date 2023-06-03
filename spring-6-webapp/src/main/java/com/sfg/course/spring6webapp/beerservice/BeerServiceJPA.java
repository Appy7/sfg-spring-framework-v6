package com.sfg.course.spring6webapp.beerservice;

import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beerrepository.BeerRepository;
import com.sfg.course.spring6webapp.mapper.BeerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService{
    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
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
        return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beer)));
    }

    @Override
    public Optional<BeerDTO> updateBeer(UUID id, BeerDTO beer) {

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
