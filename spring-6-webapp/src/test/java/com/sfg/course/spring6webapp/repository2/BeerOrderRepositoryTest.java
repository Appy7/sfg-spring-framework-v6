package com.sfg.course.spring6webapp.repository2;

import com.sfg.course.spring6webapp.entities.Beer;
import com.sfg.course.spring6webapp.entities.BeerOrder;
import com.sfg.course.spring6webapp.entities.BeerOrderShipment;
import com.sfg.course.spring6webapp.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@DataJpaTest
class BeerOrderRepositoryTest {
    @Autowired
    BeerOrderRepository beerOrderRepository;
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer customer;
    Beer beer;

    @BeforeEach
    void setUp() {
        customer=customerRepository.findAll().get(0);
        beer=beerRepository.findAll().get(0);
    }
    @Transactional
    @Test
    void testBeerOrderRepo() {
//        System.out.println(beerOrderRepository.count());
//        System.out.println(customerRepository.count());
//        System.out.println(customer.getName());
//        System.out.println(beer.getBeerName());
        BeerOrder beerOrder=BeerOrder.builder().
                customerRef("1st customer")
                .beerOrderShipment(BeerOrderShipment.builder().trackingNumber("324234").build())
                .customer(customer).build();

        BeerOrder savedBeerOrder= beerOrderRepository.save(beerOrder);
       System.out.println(savedBeerOrder.getCustomerRef());

    }
}