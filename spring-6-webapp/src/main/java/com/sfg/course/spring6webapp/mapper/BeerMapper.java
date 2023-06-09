package com.sfg.course.spring6webapp.mapper;

import com.sfg.course.spring6webapp.model.BeerDTO;
import com.sfg.course.spring6webapp.entities.Beer;
import org.mapstruct.Mapper;

@Mapper
public interface  BeerMapper {
   BeerDTO beerToBeerDto(Beer beer);
   Beer beerDtoToBeer(BeerDTO beerDTO);

}
