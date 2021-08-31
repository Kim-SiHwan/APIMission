package kim.sihwan.mission.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TypeResponseDto {
    private final String type;
    private final String value;

    public static TypeResponseDto toDto(String type, String value){
        return TypeResponseDto.builder()
                .type(type)
                .value(value)
                .build();
    }
}
