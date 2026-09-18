package com.example.buoi1.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void studentCrudWorks() throws Exception {
        String student = """
                {
                  "id": 1,
                  "name": "Nguyen Van A",
                  "address": "Ha Noi",
                  "gender": "Nam",
                  "status": "Dang hoc"
                }
                """;

        mockMvc.perform(post("/api/students")
                        .contentType("application/json")
                        .content(student))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nguyen Van A"));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("Ha Noi"));

        String updatedStudent = """
                {
                  "id": 1,
                  "name": "Nguyen Van B",
                  "address": "Da Nang",
                  "gender": "Nam",
                  "status": "Tot nghiep"
                }
                """;

        mockMvc.perform(put("/api/students/1")
                        .contentType("application/json")
                        .content(updatedStudent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Nguyen Van B"))
                .andExpect(jsonPath("$.status").value("Tot nghiep"));

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").doesNotExist());
    }
}
