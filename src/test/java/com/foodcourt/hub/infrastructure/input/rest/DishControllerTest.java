package com.foodcourt.hub.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodcourt.hub.application.dto.CreateDishCommand;
import com.foodcourt.hub.application.dto.UpdateDishCommand;
import com.foodcourt.hub.application.dto.UpdateStatusDishCommand;
import com.foodcourt.hub.application.handler.IDishHandler;
import com.foodcourt.hub.domain.exception.InvalidPermissionException;
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

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DishController.class)
@Import(SecurityTestConfig.class)
class DishControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    IDishHandler handler;
    @Test
    void createDish() throws Exception {
        CreateDishCommand dishCommand = CreateDishCommand.builder().build();

        UserPrincipal principal = new UserPrincipal(1L, "correo@gmail.com", Role.OWNER);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        mockMvc.perform(
                post("/hub-service/dish/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dishCommand))
                )
            .andExpect(status().isCreated());
    }

    @Test
    void ShouldReturn403ForbiddenCreateDish() throws Exception {

        CreateDishCommand dishCommand = CreateDishCommand.builder().build();

        UserPrincipal principal = new UserPrincipal(1L, "correo@gmail.com", Role.OWNER);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        //los métodos que devuelven void no se pueden mockear con when()
        doThrow(new InvalidPermissionException()).when(handler).createDish(dishCommand, principal.id());

        mockMvc.perform(
                        post("/hub-service/dish/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dishCommand))
                )
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message")
                        .value("You do not have the permission to make this request"));
    }

//
    @Test
    void updateDish() throws Exception {
        UpdateDishCommand dishCommand = UpdateDishCommand.builder().build();

        UserPrincipal principal = new UserPrincipal(1L, "correo@gmail.com", Role.OWNER);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );
        mockMvc.perform(
                        patch("/hub-service/dish/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dishCommand))
                )
                .andExpect(status().isOk());
    }

    @Test
    void updateStatusDish() throws Exception {

        UpdateStatusDishCommand dishCommand = UpdateStatusDishCommand.builder().build();

        UserPrincipal principal = new UserPrincipal(1L, "correo@gmail.com", Role.OWNER);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        principal, null, Collections.singleton(() -> "ROLE_" + principal.role())
                )
        );

        mockMvc.perform(
                        patch("/hub-service/dish/status")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dishCommand))
                )
                .andExpect(status().isOk());
        }
}