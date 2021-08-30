package kim.sihwan.mission.service;

import kim.sihwan.mission.exception.customException.InvalidProductTypeException;
import kim.sihwan.mission.exception.customException.ProductNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RootServiceTest {

    @Autowired
    RootService rootService;

    @ParameterizedTest
    @ValueSource(strings = {"과일", "채소"})
    void 목록조회_정상요청(final String type) {
        assertDoesNotThrow(() -> rootService.requestProductList(type));
    }

    @ParameterizedTest
    @ValueSource(strings = {"과자", "사탕"})
    void 목록조회_없는유형_요청(final String type) {
        assertThrows(InvalidProductTypeException.class, () -> rootService.requestProductList(type));
    }

    @ParameterizedTest
    @MethodSource("successParameterConstructor")
    void 가격조회_정상요청(final String type, final String name) {
        assertDoesNotThrow(() -> rootService.requestProductInfo(type, name));
    }

    @ParameterizedTest
    @MethodSource("failParameterConstructor")
    void 가격조회_없는상품_요청(final String type, final String name) {
        assertThrows(ProductNotFoundException.class, () -> rootService.requestProductInfo(type, name));
    }

    static Stream<Arguments> successParameterConstructor() {
        return Stream.of(
                Arguments.arguments("과일", "배"),
                Arguments.arguments("과일", "사과"),
                Arguments.arguments("채소", "치커리"),
                Arguments.arguments("채소", "토마토")
        );
    }

    static Stream<Arguments> failParameterConstructor() {
        return Stream.of(
                Arguments.arguments("과일", "감자"),
                Arguments.arguments("과일", "단감"),
                Arguments.arguments("채소", "고구마"),
                Arguments.arguments("채소", "키위")
        );
    }
}
