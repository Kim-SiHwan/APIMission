package kim.sihwan.mission.service;

import kim.sihwan.mission.exception.customException.VegetableNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class VegetableServiceTest {

    @Autowired
    VegetableService vegetableService;

    @Test
    void 채소_목록조회_정상요청() {
        assertDoesNotThrow(() -> vegetableService.requestVegetableList());
    }

    @ParameterizedTest
    @ValueSource(strings = {"치커리","토마토","깻잎","상추"})
    void 채소_가격조회_정상요청(final String name) {
        assertDoesNotThrow(() -> vegetableService.requestVegetableInfo(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"양파","고추","양배추","양상추"})
    void 채소_없는상품_조회요청(final String name) {
        assertThrows(VegetableNotFoundException.class,() -> vegetableService.requestVegetableInfo(name));
    }

}
