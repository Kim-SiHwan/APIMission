package kim.sihwan.mission.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FruitResponseDto {

    @ApiModelProperty(name = "상품의 이름")
    private final String name;

    @ApiModelProperty(name = "상품의 가격")
    private final String price;

    public static FruitResponseDto toDto(String name, String price){
        return FruitResponseDto.builder()
                .name(name)
                .price(price)
                .build();
    }
}
