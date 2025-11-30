package com.foodcourt.hub.infrastructure.input.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.hub.application.dto.order.CreateOrderCommand;
import com.foodcourt.hub.application.dto.order.CreateOrderResponse;
import com.foodcourt.hub.application.dto.order.MarkOrderAsDeliveredCommand;
import com.foodcourt.hub.application.handler.IOrderHandler;
import com.foodcourt.hub.domain.model.Role;
import com.foodcourt.hub.infrastructure.security.SecurityTestConfig;
import com.foodcourt.hub.infrastructure.security.UserPrincipal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(SecurityTestConfig.class)
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IOrderHandler handler;

    @Test
    void createOrder() throws Exception {

        CreateOrderCommand command = CreateOrderCommand.builder().build();

        UserPrincipal principal = new UserPrincipal(1L, "correo@gmail.com", Role.CLIENT);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        CreateOrderResponse mockResponse = CreateOrderResponse.builder()
                .id(1l)
                .status("PENDING")
                .restaurantId(1l)
                .build();

        when(handler.createOrder(command, principal.id())).thenReturn(mockResponse);

        mockMvc.perform(
                post("/hub-service/order/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command))
        ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1l))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.restaurantId").value(1l));
    }

    @Test
    void shouldAssignOrder() throws Exception{
        Long orderId = 1l;

        UserPrincipal principal = new UserPrincipal(2L, "correo@gmail.com", Role.EMPLOYEE);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        mockMvc.perform(
                patch("/hub-service/order/{orderId}/assign", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderId))
        ).andExpect(status().isOk());

        verify(handler).assignOrder(orderId, principal.id());
    }

    @Test
    void  shouldMarkOrderAsDelivered() throws Exception {

        Long orderId = 1l;
        MarkOrderAsDeliveredCommand command = MarkOrderAsDeliveredCommand.builder()
                        .orderId(1l).pin("1234").build();

        UserPrincipal principal = new UserPrincipal(2L, "correo@gmail.com", Role.EMPLOYEE);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        mockMvc.perform(
                post("/hub-service/order/delivered")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command))
        ).andExpect(status().isOk());

        verify(handler).markOrderAsDelivered(command, principal.id());
    }
}