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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class FruitControllerTest {

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
    @DisplayName("과일 목록을 조회하는 테스트")
    public void 과일목록_조회테스트 () throws Exception {

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/fruit"));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]",is("배")))
                .andExpect(jsonPath("$[1]",is("토마토")))
                .andExpect(jsonPath("$[2]",is("사과")))
                .andExpect(jsonPath("$[3]",is("바나나")))
                .andDo(print());

    }

    @Test
    @DisplayName("과일 정보를 조회하는 테스트")
    public void 과일정보_조회테스트() throws Exception {

        //given
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("type","과일");
        map.add("name","사과");

        //when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/fruit/detail")
                        .params(map));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("사과")))
                .andDo(print());

    }

    @Test
    @DisplayName("빈 과일명으로 조회하는 테스트 E01 에러코드를 반환")
    public void 과일정보_조회실패_테스트_V1() throws Exception {

        //given
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("type","과일");
        map.add("name","   ");

        //when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/fruit/detail")
                        .params(map));

        //then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code",is("E01")))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 과일명으로 조회하는 테스트 E03 에러코드를 반환")
    public void 과일정보_조회실패_테스트_V2() throws Exception {

        //given
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.add("type","과일");
        map.add("name","감자");

        //when
        ResultActions resultActions = mockMvc
                .perform(get("/api/v1/fruit/detail")
                        .params(map));

        //then
        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code",is("E03")))
                .andDo(print());
    }
}
