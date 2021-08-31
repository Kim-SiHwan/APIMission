package kim.sihwan.mission.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FruitResponseDto {
    private final String name;
    private final String price;

    public static FruitResponseDto toDto(String name, String price){
        return FruitResponseDto.builder()
                .name(name)
                .price(price)
                .build();
    }
}
