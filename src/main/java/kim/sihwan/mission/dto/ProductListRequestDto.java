package kim.sihwan.mission.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductListRequestDto {

    @NotBlank(message = "상품 유형은 비어있을 수 없습니다.")
    private String type;
}
