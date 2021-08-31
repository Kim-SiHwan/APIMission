package kim.sihwan.mission.service;

import kim.sihwan.mission.exception.customException.FruitNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FruitServiceTest {

    @Autowired
    FruitService fruitService;

    @Test
    void 과일_목록조회_정상요청() {
        assertDoesNotThrow(() -> fruitService.requestFruitList());
    }

    @ParameterizedTest
    @ValueSource(strings = {"사과","배","바나나","토마토"})
    void 과일_가격조회_정상요청(final String name) {
        assertDoesNotThrow(() -> fruitService.requestFruitInfo(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"감자","고구마","키위","단감"})
    void 과일_없는상품_조회요청(final String name) {
        assertThrows(FruitNotFoundException.class,() -> fruitService.requestFruitInfo(name));
    }

}
