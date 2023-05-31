package com.sfg.course.spring6webapp.beerrepository;

import com.sfg.course.spring6webapp.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
