package kim.sihwan.mission.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductInfoRequestDto {

    @NotBlank(message = "상품 유형은 필수값입니다.")
    private String type;

    @NotBlank(message = "상품명은 필수값입니다.")
    private String name;

}
