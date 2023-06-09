package com.sfg.course.spring6webapp.bootstrap;

import com.sfg.course.spring6webapp.repository2.CustomerRepository;
import com.sfg.course.spring6webapp.service.BeerCSVService;
import com.sfg.course.spring6webapp.entities.Customer;
import com.sfg.course.spring6webapp.model.BeerCSVRecord;
import com.sfg.course.spring6webapp.model.BeerStyle;
import com.sfg.course.spring6webapp.repository2.BeerRepository;
import com.sfg.course.spring6webapp.entities.Beer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapBeerAndCustomerData implements CommandLineRunner {
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private  final BeerCSVService beerCSVService;
    @Transactional
    @Override
    public void run(String... args) throws Exception {
        fillBeerData();
        loadCustomerData();
        fillBeerCSVData();

    }

    private void fillBeerCSVData() throws FileNotFoundException {

        if(beerRepository.count()<10){
          File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
          List<BeerCSVRecord> records=beerCSVService.convertCSVToPOJO(file);
            records.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American IPA", "American Double / Imperial IPA",
                            "Belgian IPA","American Pale Ale (APA)", "American Black Ale",
                            "Belgian Dark Ale", "American Blonde Ale" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout","Saison / Farmhouse Ale" -> BeerStyle.STOUT;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 30))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });
        }
    }

    public void fillBeerData() {
        if (beerRepository.count() == 0) {
            Beer b1 = Beer.builder()
                    .beerName("jhjkh")
                    .beerStyle(BeerStyle.LAGER)
                    .upc("312")
                    .price(new BigDecimal("12.56"))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            Beer b2 = Beer.builder()
                    .beerName("bvbnv")
                    .beerStyle(BeerStyle.AMBER_ALE)
                    .upc("99")
                    .price(new BigDecimal("12.55"))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            Beer b3 = Beer.builder()
                    .beerName("sdzasda")
                    .beerStyle(BeerStyle.STOUT)
                    .upc("323")
                    .price(new BigDecimal("12.50"))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(b1);
            beerRepository.save(b2);
            beerRepository.save(b3);

        }
    }
        private void loadCustomerData () {

            if (customerRepository.count() == 0) {
                Customer customer1 = Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Customer 1")
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();

                Customer customer2 = Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Customer 2")
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();

                Customer customer3 = Customer.builder()
                        .id(UUID.randomUUID())
                        .name("Customer 3")
                        .version(1)
                        .createdDate(LocalDateTime.now())
                        .updateDate(LocalDateTime.now())
                        .build();

                //customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));

                customerRepository.save(customer1);
                customerRepository.save(customer2);
                customerRepository.save(customer3);
            }
        }



}
