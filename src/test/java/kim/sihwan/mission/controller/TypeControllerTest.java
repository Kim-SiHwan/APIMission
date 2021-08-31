package kim.sihwan.mission.controller;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TypeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ctx;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .build();
    }

    @Test
    @DisplayName("Enum에 추가되어있는 상품 유형을 반환하는 테스트")
    public void 상품_유형_조회테스트() throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/type"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type",is("fruit")))
                .andExpect(jsonPath("$[0].value",is("과일")))
                .andExpect(jsonPath("$[1].type",is("vegetable")))
                .andExpect(jsonPath("$[1].value",is("채소")))
                .andDo(print());
    }
}

