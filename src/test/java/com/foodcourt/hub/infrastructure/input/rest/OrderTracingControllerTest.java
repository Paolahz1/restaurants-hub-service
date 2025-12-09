package com.foodcourt.hub.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.hub.application.dto.order.GetEmployeeRankingResponse;
import com.foodcourt.hub.application.dto.order.GetTracingOrderDurationResponse;
import com.foodcourt.hub.application.dto.order.GetTracingOrderByClientResponse;
import com.foodcourt.hub.application.dto.order.TracingOrderResponse;
import com.foodcourt.hub.application.handler.IOrderHandler;
import com.foodcourt.hub.domain.model.OrderStatus;
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
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderTracingController.class)
@Import(SecurityTestConfig.class)
class OrderTracingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IOrderHandler handler;

    @Test
    void shouldGetTracingOrderByClientAndOrderId() throws Exception {
        long orderId = 1L;

        UserPrincipal principal = new UserPrincipal(10L, "client@mail.com", Role.CLIENT);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        List<TracingOrderResponse> tracingOrderResponseList =
                List.of(TracingOrderResponse.builder().status(OrderStatus.PENDING).build());

        GetTracingOrderByClientResponse mockResponse = GetTracingOrderByClientResponse.builder()
                .orderId(orderId)
                .restaurantId(5L)
                .tracingOrder(tracingOrderResponseList)
                .build();

        when(handler.getTracingOrderByClient(orderId, principal.id())).thenReturn(mockResponse);

        mockMvc.perform(
                        get("/hub-service/tracing/orderId/{orderId}", orderId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(orderId))
                .andExpect(jsonPath("$.restaurantId").value(5L))
                .andExpect(jsonPath("$.tracingOrder[0].status").value("PENDING"));

        verify(handler).getTracingOrderByClient(orderId, principal.id());
    }



    @Test
    void shouldGetTracingOrderByRestaurant() throws Exception {
        long restaurantId = 5L;
        long ownerId = 1l;
        UserPrincipal principal = new UserPrincipal(20L, "owner@mail.com", Role.OWNER);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        GetTracingOrderDurationResponse mockResponse = GetTracingOrderDurationResponse.builder()
                .restaurantId(restaurantId)
                .build();

        when(handler.getOrderDuration(restaurantId, ownerId)).thenReturn(mockResponse);

        mockMvc.perform(
                        get("/hub-service/tracing/restaurantId/{restaurantId}", restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(restaurantId));

        verify(handler).getOrderDuration(restaurantId, ownerId);
    }

    @Test
    void shouldGetRankingEmployees() throws Exception {
        long restaurantId = 5L;
        long ownerId = 1l;

        UserPrincipal principal = new UserPrincipal(20L, "owner@mail.com", Role.OWNER);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        GetEmployeeRankingResponse mockResponse = GetEmployeeRankingResponse.builder()
                .restaurantId(restaurantId)
                .build();

        when(handler.getEmployeeRanking(restaurantId, ownerId)).thenReturn(mockResponse);

        mockMvc.perform(
                        get("/hub-service/tracing/restaurant/ranking/{restaurantId}", restaurantId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(restaurantId));

        verify(handler).getEmployeeRanking(restaurantId, ownerId);
    }
}
