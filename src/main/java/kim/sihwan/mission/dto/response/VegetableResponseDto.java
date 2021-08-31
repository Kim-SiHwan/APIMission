package kim.sihwan.mission.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VegetableResponseDto {
    private final String name;
    private final String price;

    public static VegetableResponseDto toDto(String name, String price){
        return VegetableResponseDto.builder()
                .name(name)
                .price(price)
                .build();
    }
}
