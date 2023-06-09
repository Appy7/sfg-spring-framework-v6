package com.sfg.course.spring6webapp.repository2;

import com.sfg.course.spring6webapp.entities.Beer;
import com.sfg.course.spring6webapp.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BeerRepository beerRepository;

    Beer beer;

    @BeforeEach
    void setUp() {
       beer= beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testCategory() {
        Category category=categoryRepository.save(Category.builder()
                .description("normal beer").build());
        beer.addCategoryToCategories(category);
        Beer savedBeer=beerRepository.save(beer);
        System.out.println(savedBeer.getBeerName());
    }
}