package com.bravechen.springboot.mybatisplusdemo.rest;

import com.bravechen.springboot.mybatisplusdemo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    // 类似于junit4的@Before
    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void getUser() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/user/1").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8");
        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("管理员"))
                .andDo(MockMvcResultHandlers.print());

    }
}