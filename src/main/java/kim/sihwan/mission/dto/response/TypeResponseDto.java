package kim.sihwan.mission.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TypeResponseDto {

    @ApiModelProperty(name = "상품의 유형")
    private final String type;

    @ApiModelProperty(name = "상품의 이름")
    private final String value;

    public static TypeResponseDto toDto(String type, String value){
        return TypeResponseDto.builder()
                .type(type)
                .value(value)
                .build();
    }
}
