package com.example.mvcdemo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //新增 api
    @Test
    @Transactional
    public void insert() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"test\"\n" +
                        "}");
        mockMvc.perform(requestBuilder)
                .andExpect(content().string("執行 INSERT sql"));
    }

    //查詢 api
    @Test
    public void select() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/{studentId}", 4);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("Jerry"));
    }

    //修改 api
    @Test
    @Transactional
    public void update() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/students/{studentId}", 4)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\": \"test\"\n" +
                        "}");
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("執行 UPDATE sql"));
    }

    //刪除 api
    @Test
    @Transactional
    public void delete() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/students/{studentId}", 4);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().string("執行 DELETE sql"));
    }
}