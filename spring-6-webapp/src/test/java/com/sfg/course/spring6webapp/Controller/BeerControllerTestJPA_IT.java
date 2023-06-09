package com.sfg.course.spring6webapp.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfg.course.spring6webapp.model.BeerDTO;
import com.sfg.course.spring6webapp.repository2.BeerRepository;
import com.sfg.course.spring6webapp.entities.Beer;
import com.sfg.course.spring6webapp.mapper.BeerMapper;
import com.sfg.course.spring6webapp.model.BeerStyle;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    //ObjectOptimisticLockingFailureException test
    //@Transactional
    @Test
    void updateBeerNameBadVersion() throws Exception {
       Beer beer= beerRepository.findAll().get(1);
       BeerDTO beerDTO=beerMapper.beerToBeerDto(beer);

       beerDTO.setBeerName("1st update");
       MvcResult mvcResult1= mockMvc.perform(put
               (BeerController.BEER_PATH_WITH_ID,beer.getId())
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(beerDTO)))
               .andExpect(status().isNoContent())
               .andReturn();
      System.out.println(mvcResult1.getResponse().getContentAsString());

        beerDTO.setBeerName("2nd update");
        MvcResult mvcResult2= mockMvc.perform(put
                        (BeerController.BEER_PATH_WITH_ID,beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerDTO)))
                .andExpect(status().isNoContent())
                .andReturn();
        System.out.println(mvcResult2.getResponse().getContentAsString());
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryTruePage2() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "true")
                        .queryParam("pageNumber","2")
                        .queryParam("pageSize","25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)))
                .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.notNullValue()));
    }
    @Test
    void tesListBeersByStyleAndNameShowInventoryTrue() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "true")
                        .queryParam("pageNumber","2")
                        .queryParam("pageSize","25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(11)))
                .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryFalse() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName", "IPA")
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("showInventory", "false")
                .queryParam("pageNumber", "2")
                .queryParam("pageSize","800"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(11)))
                .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.nullValue()));
    }

    @Test
    void testListBeersByNameAndBeerStyle() throws Exception {

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerName","IPA")
                        .queryParam("beerStyle",BeerStyle.IPA.name())
                        .queryParam("pageSize", "800")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",is(1)));
    }

    @Test
    void testListBeersByName() throws Exception {

        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName","IPA")
                        .queryParam("pageSize", "800")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",is(329)));
    }

    @Test
    void testListBeersByBeerStyle() throws Exception {

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("pageSize", "800")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()",is(947)));
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
        Page<BeerDTO> dtoList=beerController.getBeers(null,null, false, 1, 2413);

        assertThat(dtoList.getContent().size()).isEqualTo(1000);
    }

    @Rollback
    @Transactional
    @Test
    void testGetEmptyBeers(){

        beerRepository.deleteAll();

        Page<BeerDTO> dtoList=beerController.getBeers(null,null, false, 1, 25);

        assertThat(dtoList.getContent().size()).isEqualTo(0);
    }


}