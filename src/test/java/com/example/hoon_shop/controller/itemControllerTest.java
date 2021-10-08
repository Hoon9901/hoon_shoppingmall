package com.example.hoon_shop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class itemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")  // 테스트시 유저 이름, 권한 설정
    public void 상품등록_페이지권한_테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))  // get 요청
                .andDo(print()) // 요청, 응답 메시지 콘솔에 출력
                .andExpect(status().isOk());    // 응답 상태 코드가 정상인지 확인
    }

    @Test
    @WithMockUser(username = "guest", roles = "GUEST")
    public void 상품등록_페이지_게스트접근_권한_테스트() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))  // get 요청
                .andDo(print()) // 요청, 응답 메시지 콘솔에 출력
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void 상품등록_페이지_일반회원접근_권한_테스트() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/item/new"))  // get 요청
                .andDo(print()) // 요청, 응답 메시지 콘솔에 출력
                .andExpect(status().isForbidden());
    }

}