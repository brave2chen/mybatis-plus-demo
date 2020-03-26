package com.bravechen.springboot.mybatisplusdemo.rest;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bravechen.springboot.mybatisplusdemo.entity.User;
import com.bravechen.springboot.mybatisplusdemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
// 自动回滚测试的DB操作
@Transactional
class UserControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    // 类似于junit4的@Before
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }

    @Test
    void get() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8");
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("admin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nickname").value("管理员"))
        ;
    }

    @Test
    void save() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(new HashMap() {{
                    put(User.USERNAME, "test_save");
                    put(User.NICKNAME, "test_save");
                    put(User.MOBILE, "test_save");
                    put(User.PASSWORD, "test_save");
                }}));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Boolean.toString(true)))
        ;

        // 验证是否入库成功
        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername, "test_save");
        User user = userService.getOne(wrapper);
        assertAll("test_save",
                () -> assertNotNull(user),
                () -> assertNotNull(user.getCreateUser()),
                () -> assertNotNull(user.getCreateTime()),
                () -> assertEquals(user.getNickname(), "test_save")
        );
    }

    @Test
    void update() throws Exception {
        LocalDateTime before = LocalDateTime.now();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/user/1")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(new ObjectMapper().writeValueAsString(new HashMap() {{
                    // 其他字段会使用默认值进行更新，无默认值的，即为null，不会更新
                    // 字段更新策略请查看：mybatis-plus.global-config.db-config.update-strategy
                    put(User.NICKNAME, "test_update");
                }}));

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Boolean.toString(true)))
        ;

        // 验证是否入库成功
        User user = userService.getById(1);
        assertAll("test_update",
                () -> assertNotNull(user),
                () -> assertEquals(user.getNickname(), "test_update"),
                () -> assertNotNull(user.getUsername()),
                () -> assertTrue(user.getUpdateTime().compareTo(before) > 0)
        );
    }

    @Test
    void delete() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8");

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Boolean.toString(true)))
        ;

        // 验证是否入库成功
        User user = userService.getById(1);
        assertNull(user);
    }

    @Test
    void page() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user/page")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                // 分页查询1W条后的数据，返回空列表
                .param("currentPage", "100")
                .param("pageSize", "100");

        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.current").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.records").isEmpty())
        ;
    }
}