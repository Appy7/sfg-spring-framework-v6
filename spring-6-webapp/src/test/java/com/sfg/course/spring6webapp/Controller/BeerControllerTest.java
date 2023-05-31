package com.sfg.course.spring6webapp.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sfg.course.spring6webapp.beer.BeerDTO;
import com.sfg.course.spring6webapp.beerservice.BeerService;
import com.sfg.course.spring6webapp.beerservice.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
//import org.assertj.core.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
//import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
//@SpringBootTest
class BeerControllerTest {
//@Autowired
//    private BeerController beerController;
    @Autowired
    MockMvc mockMvc;

    @MockBean
    BeerService beerService;

    @Autowired
    ObjectMapper objectMapper;

    BeerServiceImpl beerServiceImpl;

    @BeforeEach
    void setUp(){
        //beerController=new BeerController();
        beerServiceImpl=new BeerServiceImpl();
    }

    @Test
    void getBeerByIdInController() throws Exception {

        BeerDTO beer=beerServiceImpl.getListOfBeers().get(0);

        given(beerService.getBeerById(beer.getId())).willReturn(Optional.of(beer));

        mockMvc.perform(get(BeerController.BEER_PATH+"/"+beer.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(beer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is(beer.getBeerName())));
        //System.out.println(beerController.getBeerByIdInController(UUID.randomUUID()));
    }
    @Test
    void testListOfBeers() throws Exception {

        given(beerService.getListOfBeers()).willReturn(beerServiceImpl.getListOfBeers());

        mockMvc.perform(get(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));
    }
    @Test
    void testCreateBeer() throws Exception {
        //String jsonObject=objectMapper.writeValueAsString(beer);
        //System.out.println(jsonObject);

        BeerDTO beer= beerServiceImpl.getListOfBeers().get(0);
        beer.setVersion(null);
        beer.setId(null);

        given(beerService.saveBeer(any(BeerDTO.class))).willReturn(beerServiceImpl.getListOfBeers().get(1));

        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));
         }

         @Test
         void testUpdateBeerByID() throws Exception {
         BeerDTO beer= beerServiceImpl.getListOfBeers().get(1);

         mockMvc.perform(put(BeerController.BEER_PATH_WITH_ID,beer.getId())
                 .accept(MediaType.APPLICATION_JSON)
                 .contentType(MediaType.APPLICATION_JSON)
                 .content(objectMapper.writeValueAsString(beer)))
                 .andExpect(status().isNoContent());

         verify(beerService).updateBeer(any(UUID.class),any(BeerDTO.class));
         }

         @Test
        void testDeleteById() throws Exception {
            BeerDTO beer=beerServiceImpl.getListOfBeers().get(1);
            mockMvc.perform(delete(BeerController.BEER_PATH_WITH_ID,beer.getId())
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
             ArgumentCaptor<UUID> uuidArgumentCaptor=ArgumentCaptor.forClass(UUID.class);
             verify(beerService).deleteBeer(uuidArgumentCaptor.capture());
             assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        }

         @Test
        void testNoTFoundException() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_WITH_ID,UUID.randomUUID()))
                .andExpect(status().isNotFound());
         }
}