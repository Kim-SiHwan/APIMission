package kim.sihwan.mission.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VegetableResponseDto {

    @ApiModelProperty(name = "상품의 이름")
    private final String name;

    @ApiModelProperty(name = "상품의 가격")
    private final String price;

    public static VegetableResponseDto toDto(String name, String price){
        return VegetableResponseDto.builder()
                .name(name)
                .price(price)
                .build();
    }
}
