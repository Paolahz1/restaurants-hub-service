package com.foodcourt.hub.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.hub.application.dto.CreateRestaurantCommand;
import com.foodcourt.hub.application.dto.CreateRestaurantResponse;
import com.foodcourt.hub.application.dto.GetPageRestaurantsResponse;
import com.foodcourt.hub.application.dto.RestaurantSummaryResponse;
import com.foodcourt.hub.application.handler.IRestaurantHandler;
import com.foodcourt.hub.domain.exception.InvalidNitFormatException;
import com.foodcourt.hub.domain.exception.NitAlreadyExistsException;
import com.foodcourt.hub.domain.exception.UserIsNotOwnerException;
import com.foodcourt.hub.infrastructure.security.SecurityTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
@Import(SecurityTestConfig.class)
class RestaurantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IRestaurantHandler handler;

    @Test
    void shouldCreateRestaurantSuccessfully() throws Exception {

        CreateRestaurantCommand command = CreateRestaurantCommand.builder().build();

        CreateRestaurantResponse response = CreateRestaurantResponse.builder()
                .restaurantId(1L)
                .restaurantName("TestRestaurant")
                .success(true).build();

        when(handler.createRestaurant(command)).thenReturn(response);

        mockMvc.perform(post("/hub-service/restaurant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.restaurantId").value(1L))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.restaurantName").value("TestRestaurant"));

    }

    @Test
    void shouldReturn409WhenRestaurantCreationFails() throws Exception {

        CreateRestaurantCommand command = CreateRestaurantCommand.builder().build();

        when(handler.createRestaurant(command)).thenThrow(new NitAlreadyExistsException());

        mockMvc.perform(post("/hub-service/restaurant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("The provided NIT is already registered"));
    }

    @Test
    void shouldReturn403WhenUserIstNotOwner() throws Exception {

        CreateRestaurantCommand command = CreateRestaurantCommand.builder().build();

        when(handler.createRestaurant(command)).thenThrow(new UserIsNotOwnerException());

        mockMvc.perform(post("/hub-service/restaurant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message").value("The user is not an owner"));
    }

    @Test
    void shouldReturn400WhenInvalidNitFormat() throws Exception {

        CreateRestaurantCommand command = CreateRestaurantCommand.builder().build();

        when(handler.createRestaurant(command)).thenThrow(new InvalidNitFormatException());

        mockMvc.perform(post("/hub-service/restaurant/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("The NIT format is invalid"));
    }


    @Test
    void shouldReturnPageOfRestaurants() throws Exception {

        GetPageRestaurantsResponse response = new GetPageRestaurantsResponse();

        response.setContent(List.of(
                RestaurantSummaryResponse.builder().name("A Restaurant").urlLogo("url1").build(),
                RestaurantSummaryResponse.builder().name("B Restaurant").urlLogo("url2").build()
        ));
        response.setPage(0);
        response.setSize(2);
        response.setTotalElements(10L);
        response.setTotalPages(5);
        response.setFirst(true);
        response.setLast(false);

        when(handler.getPageRestaurants(0, 2)).thenReturn(response);

        mockMvc.perform(get("/hub-service/restaurant/page/0/size/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(2))
                .andExpect(jsonPath("$.totalElements").value(10))
                .andExpect(jsonPath("$.totalPages").value(5))
                .andExpect(jsonPath("$.first").value(true))
                .andExpect(jsonPath("$.last").value(false))
                .andExpect(jsonPath("$.content[0].name").value("A Restaurant"))
                .andExpect(jsonPath("$.content[0].urlLogo").value("url1"))
                .andExpect(jsonPath("$.content[1].name").value("B Restaurant"))
                .andExpect(jsonPath("$.content[1].urlLogo").value("url2"));
    }

}
