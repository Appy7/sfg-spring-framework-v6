package com.sfg.course.spring6webapp.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beerrepository.BeerRepository;
import com.sfg.course.spring6webapp.entities.Beer;
import com.sfg.course.spring6webapp.mapper.BeerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class BeerControllerTestJPA {

    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerController beerController;

    @Autowired
    BeerMapper beerMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testUpdateBeerByIdIT() throws Exception {
        Beer beer= beerRepository.findAll().get(0);
        beer.setBeerName("aparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparnaaparna");

       MvcResult mvcResult= mockMvc.perform(put(BeerController.BEER_PATH_WITH_ID,beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.length()",is(1))).andReturn();

       System.out.println(((MvcResult) mvcResult).getResponse().getContentAsString());

    }

    @Test
    void deleteNotFoundException() {

        assertThrows(NotFoundException.class,
                ()->beerController.deleteById(UUID.randomUUID()));
    }
    @Rollback
    @Transactional
    @Test
    void testDeleteByID() {
        Beer beer=beerRepository.findAll().get(0);

        ResponseEntity responseEntity=beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId())).isEmpty();
    }

    @Test
    void updateNotFoundException() {

        assertThrows(NotFoundException.class,
                ()->beerController.updateBeerById(UUID.randomUUID(),
                        BeerDTO.builder().build()));
    }
    @Rollback
    @Transactional
    @Test
    void testUpdateBeer() {
        Beer beer=beerRepository.findAll().get(0);
        BeerDTO beerDTO=beerMapper.beerToBeerDto(beer);
        final String beerName="Updated Beername";
        beerDTO.setVersion(null);
        beerDTO.setId(null);
        beerDTO.setBeerName(beerName);
       ResponseEntity responseEntity= beerController.updateBeerById(beer.getId(),beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));


        Beer beer1=beerRepository.findById(beer.getId()).get();

        assertThat(beer1.getBeerName()).isEqualTo(beerName);

    }

    @Rollback
    @Transactional
    @Test
    void testSavebeer() {
        BeerDTO beerDTO=BeerDTO.builder().beerName("Temp Beer").build();
        ResponseEntity responseEntity= beerController.postBeer(beerDTO);

       assertThat(responseEntity.getStatusCode())
               .isEqualTo(HttpStatusCode.valueOf(201));
       assertThat(responseEntity.
               getHeaders().getLocation()).isNotNull();

       String[] uuidstring=responseEntity.getHeaders().getLocation().getPath().split("/");

       UUID savedUUID=UUID.fromString(uuidstring[4]);

        System.out.println(Arrays.toString(uuidstring));
        Beer beer=beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testExceptionNotFound() {
        assertThrows(NotFoundException.class,()->beerController.getBeerByIdInController(UUID.randomUUID()));
    }

    @Test
    void testExceptionHappyPath() {

      Beer beer=  beerRepository.findAll().get(0);
      BeerDTO beerDTO=beerController.getBeerByIdInController(beer.getId());
      assertThat(beerDTO).isNotNull();

    }

    @Test
    void testGetAllBeers(){
        List<BeerDTO> dtoList=beerController.getBeers();

        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testGetEmptyBeers(){

        beerRepository.deleteAll();

        List<BeerDTO> dtoList=beerController.getBeers();

        assertThat(dtoList.size()).isEqualTo(0);
    }


}