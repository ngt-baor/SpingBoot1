package com.example.buoi1.controller;

import com.example.buoi1.repo.DrinkRepo;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Optional;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.example.buoi1.model.Drink;

class DrinkControllerTest {

    @Test
    void listsDrinksAtApiEndpoint() throws Exception {
        DrinkRepo drinkRepo = mock(DrinkRepo.class);
        when(drinkRepo.findAll()).thenReturn(List.of());

        MockMvc mockMvc = mockMvc(drinkRepo);

        mockMvc.perform(get("/api/drinks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void createsDrink() throws Exception {
        DrinkRepo drinkRepo = mock(DrinkRepo.class);
        when(drinkRepo.save(any(Drink.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc(drinkRepo).perform(post("/api/drinks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Coffee\",\"price\":20000,\"active\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Coffee"))
                .andExpect(jsonPath("$.price").value(20000));
    }

    @Test
    void updatesDrink() throws Exception {
        Drink existing = new Drink();
        existing.setId(1);
        DrinkRepo drinkRepo = mock(DrinkRepo.class);
        when(drinkRepo.findById(1)).thenReturn(Optional.of(existing));
        when(drinkRepo.save(any(Drink.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc(drinkRepo).perform(put("/api/drinks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Tea\",\"price\":15000,\"active\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tea"))
                .andExpect(jsonPath("$.price").value(15000));
    }

    @Test
    void deletesDrink() throws Exception {
        DrinkRepo drinkRepo = mock(DrinkRepo.class);
        when(drinkRepo.existsById(1)).thenReturn(true);

        mockMvc(drinkRepo).perform(delete("/api/drinks/1"))
                .andExpect(status().isNoContent());

        verify(drinkRepo).deleteById(1);
    }

    private MockMvc mockMvc(DrinkRepo drinkRepo) {
        DrinkController controller = new DrinkController();
        controller.drinkRepo = drinkRepo;
        return standaloneSetup(controller).build();
    }
}
